package com.movefeng.hexoblogadmin.service;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.dao.ArticleDao;
import com.movefeng.hexoblogadmin.dao.CommentDao;
import com.movefeng.hexoblogadmin.dao.UserDao;
import com.movefeng.hexoblogadmin.model.Article;
import com.movefeng.hexoblogadmin.model.SystemConfig;
import com.movefeng.hexoblogadmin.model.VisitRecord;
import com.movefeng.hexoblogadmin.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author z
 */
@Service
@Slf4j
public class ArticleService {

    @Resource
    private CommentDao commentDao;

    @Resource
    private UserDao userDao;

    @Resource
    private ArticleDao articleDao;

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private HttpServletRequest request;

    /**
     * 查询所有文章信息
     *
     * @return
     */
    public Page<ArticleVO> queryArticle(Map<String, Object> searchParam) {
        return articleDao.selectArticleVOList(searchParam);
    }

    /**
     * 批量更新数据
     *
     * @param articleList 磁盘中读到的最新的文章文件信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleBatch(List<Article> articleList) {

        List<Article> articlesOld = articleDao.selectArticleList();
        // 新数据中有的而旧数据中没有的就是需要insert的数据
        List<Article> toInsert = articleList.stream().filter(x -> !articlesOld.contains(x)).collect(Collectors.toList());
        // 旧数据中有的而新数据中没有的就是需要delete的数据
        List<Article> toDelete = articlesOld.stream().filter(x -> !articleList.contains(x)).collect(Collectors.toList());
        if (toDelete.size() > 0) {
            articleDao.deleteBatch(toDelete);
        }
        if (toInsert.size() > 0) {
            articleDao.insertArticleBatch(toInsert);
        }

    }

    /**
     * 根据文章名获取文章内容
     *
     * @param title
     * @return
     */
    public Result getArticleContent(String title) {
        Result<String> result = new Result<>(Result.Code.ERROR);
        try {
            String articlePath = systemConfig.getArticlePath();
            File file = new File(articlePath, title + ".md");
            if (file.exists()) {
                String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                result.setCode(Result.Code.SUCCESS);
                result.setData(content);
            } else {
                result.setCode(Result.Code.ERROR);
                result.setMessage("文章不存在！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保存文章到本地文件
     *
     * @param title
     * @param content
     * @return
     */
    public Result saveArticle(String oldTitle, String title, String content) {
        Result<String> result = new Result<>(Result.Code.ERROR);
        try {
            String articlePath = systemConfig.getArticlePath();
            File file = new File(articlePath, oldTitle + ".md");
            if (!isLegalFilename(file.getName())) {
                result.setMessage("文件名不合法！");
                return result;
            }
            // 判断源文件是否存在
            if (file.exists()) {
                // 判断新标题和原标题是否一样
                if (oldTitle.equals(title)) {
                    FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
                    result.setCode(Result.Code.SUCCESS);
                    result.setMessage("修改成功！");
                } else {
                    File newFile = new File(articlePath, title + ".md");
                    // 判断新文件是否已被创建
                    if (newFile.exists()) {
                        result.setMessage("标题已存在！");
                    } else {
                        // 将源文件重命名为新文件
                        boolean b = file.renameTo(newFile);
                        if (b) {
                            result.setCode(Result.Code.SUCCESS);
                            result.setMessage("保存成功！");
                            FileUtils.writeStringToFile(newFile, content, StandardCharsets.UTF_8);
                        } else {
                            result.setMessage("重命名失败！");
                        }
                    }
                }
            } else {
                result.setCode(Result.Code.ERROR);
                result.setMessage("文章不存在！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成文章，通过调用hexo-cli来生成文章，然后将生成的静态文件复制到http-server的可访问路径下
     *
     * @return
     */
    public Result generate() {
        Result<String> result = new Result<>(Result.Code.ERROR);
        try {
            String hexoPath = systemConfig.getHexoPath();
            String publicPath = systemConfig.getPublicPath();

            String os = System.getProperty("os.name");

            String[] windowsCommands = {
                    "cmd.exe",
                    String.format("/c cd /d %s && hexo1 g", hexoPath)
            };

            String[] linuxCommands = {
                    "/bin/bash",
                    String.format("-c cd %s && hexo g", hexoPath)
            };
            // 判断操作系统以执行不同的命令
            String[] commands = os.contains("Windows") ? windowsCommands : linuxCommands;

            Process process = Runtime.getRuntime().exec(commands);
            InputStream errorStream = process.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
            String errorInfo = reader.readLine();
            if (errorInfo != null) {
                StringBuilder output = new StringBuilder();
                output.append(errorInfo).append("\n");
                result.setMessage("生成失败！请检查 hexo-cli 是否已正确安装！");
                while ((errorInfo = reader.readLine()) != null) {
                    output.append(errorInfo).append("\n");
                }
                output.deleteCharAt(output.lastIndexOf("\n"));
                log.info(String.format("生成出错信息:\n%s", output.toString()));
                return result;
            }

            InputStream stream = process.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream));
            String temp;
            StringBuilder output = new StringBuilder();
            while ((temp = streamReader.readLine()) != null) {
                output.append(temp).append("\n");
            }
            if (output.lastIndexOf("\n") != -1) {
                output.deleteCharAt(output.lastIndexOf("\n"));
            }
            log.info(String.format("生成成功信息:\n%s", output.toString()));

            File srcDir = new File(hexoPath, "public");
            if (!srcDir.exists()) {
                result.setMessage("生成失败！请检查 <hexo-blog-path>/public 目录是否正确生成！");
            } else {
                result.setCode(Result.Code.SUCCESS);
                result.setMessage("生成成功！");
                FileUtils.copyDirectory(srcDir, new File(publicPath));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 创建文章
     *
     * @param title
     * @param content
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result createArticle(String title, String content) {
        Result<String> result = new Result<>(Result.Code.ERROR);
        try {
            String articlePath = systemConfig.getArticlePath();
            File file = new File(articlePath, title + ".md");
            if (!isLegalFilename(file.getName())) {
                result.setMessage("文件名不合法！");
                return result;
            }
            // 判断根据标题判断文件是否存在
            if (!file.exists()) {
                // 判断新标题和原标题是否一样
                FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
                result.setCode(Result.Code.SUCCESS);
                result.setMessage("创建成功！");
            } else {
                result.setCode(Result.Code.ERROR);
                result.setMessage("文章标题已存在！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除文章
     *
     * @param title
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result deleteArticle(String title) {
        Result<String> result = new Result<>(Result.Code.ERROR);

        String articlePath = systemConfig.getArticlePath();
        File file = new File(articlePath, title + ".md");
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                result.setCode(Result.Code.SUCCESS);
                result.setMessage("删除成功！");
            } else {
                result.setMessage("删除失败！");
            }
        } else {
            result.setMessage("文件不存在！");
        }

        return result;
    }

    /**
     * 更新文章基本信息
     *
     * @param articleVO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result update(ArticleVO articleVO) {
        Article article = articleDao.selectArticleByTitle(articleVO.getTitle());
        if (article != null) {
            article.setPath(articleVO.getPath());
            article.setVisitCount(Objects.requireNonNullElse(article.getVisitCount(), 0) + 1);
            articleDao.updateByTitle(article);
        } else {
            return new Result<>(Result.Code.ERROR);
        }
        return new Result<>(Result.Code.SUCCESS);
    }

    /**
     * 更新文章基本信息
     *
     * @return
     */
    public String recordVisitInfo() {
        String referer = request.getHeader("referer");
        String remoteAddr = "127.0.0.1".equals(request.getRemoteAddr()) ? request.getHeader("x-real-ip") : request.getRemoteAddr();
        int remotePort = request.getRemotePort();
        VisitRecord visitRecord = new VisitRecord()
                .setVisitTime(new Date())
                .setVisitUrl(referer)
                .setRemoteIp(remoteAddr)
                .setRemotePort(remotePort);
        articleDao.insertVisitRecord(visitRecord);
        return null;
    }

    /**
     * 判断文件名是否合法
     * 文件名不能包含下列任何字符：
     * \/:*?"<>|
     *
     * @param filename
     * @return
     */
    public boolean isLegalFilename(String filename) {
        return !Pattern.matches(".*[\\\\/:*?\"<>|].*", filename);
    }

    public void saveAllArticleInfo(List<Article> articleList) {
        articleDao.updateArticleBatch(articleList);
    }
}
