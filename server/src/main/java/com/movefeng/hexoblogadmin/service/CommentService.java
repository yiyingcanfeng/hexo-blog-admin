package com.movefeng.hexoblogadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.dao.AdminDao;
import com.movefeng.hexoblogadmin.dao.ArticleDao;
import com.movefeng.hexoblogadmin.dao.CommentDao;
import com.movefeng.hexoblogadmin.dao.UserDao;
import com.movefeng.hexoblogadmin.model.*;
import com.movefeng.hexoblogadmin.utils.AESUtil;
import com.movefeng.hexoblogadmin.utils.OkHttpUtil;
import com.movefeng.hexoblogadmin.utils.SendMail;
import com.movefeng.hexoblogadmin.vo.CommentResult;
import com.movefeng.hexoblogadmin.vo.CommentVO;
import com.movefeng.hexoblogadmin.vo.CommentVOs;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author z
 */
@Service
@Slf4j
public class CommentService {

    @Resource
    private CommentDao commentDao;

    @Resource
    private UserDao userDao;

    @Resource
    private ArticleDao articleDao;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private TaskExecutor taskExecutor;

    @Resource
    private HttpServletRequest request;

    @Resource
    private AdminDao adminDao;

    /**
     * 创建评论
     *
     * @param commentVO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result createComment(CommentVO commentVO) {
        String userMail = commentVO.getUserMail();
        String articleTitle = commentVO.getArticleTitle();
        // 前端传过来的username
        String voUsername = commentVO.getUsername();
        Article article = articleDao.selectArticleByTitle(articleTitle);
        // 验证邮箱
        User user = userDao.selectUserByEmail(userMail);
        if (user == null) {
            User user1 = userDao.selectUserByUsername(voUsername);
            if (user1 == null) {
                User insertUser = new User();
                insertUser.setEmail(userMail);
                insertUser.setName(voUsername);
                insertUser.setWebsite(commentVO.getUserWebsite());
                userDao.insertUser(insertUser);
                return setComment(commentVO, insertUser, article);
            } else {
                return new Result(Result.Code.ERROR, "该昵称已被使用！");
            }
        } else {
            String username = user.getName();
            // 比较前端传过来的用户名和数据库中的用户名
            if (username.equals(voUsername)) {
                return setComment(commentVO, user, article);
            } else {
                // 如果不一样则表示用户想使用新的用户名发表评论
                // 查询用户名是否已被使用
                User user1 = userDao.selectUserByUsername(voUsername);
                if (user1 == null) {
                    userDao.updateUserByEmail(commentVO);
                    return setComment(commentVO, user, article);
                } else {
                    return new Result(Result.Code.ERROR, "该昵称已被使用！");
                }
            }
        }


    }

    /**
     * 查询所有评论
     *
     * @param param
     * @return
     */
    public Page<CommentVO> queryAllComment(Map<String, Object> param) {
        if (param.get("searchDatetime") != null && !"".equals(param.get("searchDatetime"))) {
            List searchDatetime = (List) param.get("searchDatetime");
            param.put("searchDatetimeStart", searchDatetime.get(0));
            param.put("searchDatetimeEnd", searchDatetime.get(1));
        }
        Page<CommentVO> commentVOList = commentDao.selectAllComments(param);
        return commentVOList;
    }


    /**
     * 查询某篇文章下的评论
     *
     * @param articleTitle
     * @return
     */
    public Result<CommentResult> queryComment(String articleTitle) {
        CommentResult commentResult = new CommentResult();
        int commentCount = commentDao.selectCommentCount(articleTitle);
        commentResult.setCommentCount(commentCount);
        List<CommentVOs> parentComments = commentDao.selectCommentByArticleTitle(articleTitle);
        for (CommentVOs parentComment : parentComments) {
            Integer id = parentComment.getComment().getId();
            List<CommentVO> childComments = commentDao.selectChildComment(articleTitle, id);
            int count = commentDao.selectChildCommentCount(articleTitle, id);
            parentComment.setChildCommentCount(count);
            parentComment.setChildComment(childComments);
        }
        commentResult.setCommentVOs(parentComments);
        return new Result<>(Result.Code.SUCCESS, commentResult);
    }


    /**
     * 设置comment属性并返回Result
     *
     * @param commentVO
     * @param user
     * @param article
     * @return
     */
    private Result setComment(CommentVO commentVO, User user, Article article) {
        Comment comment = new Comment();
        if (article != null) {
            comment.setArticleId(article.getId());
            comment.setAuditStatus(Comment.AuditStatus.WAIT_AUDIT);
            comment.setContent(commentVO.getContent());
            comment.setCreateTime(new Date());
            comment.setUserId(user.getId());
            comment.setReplyUserId(commentVO.getReplyUserId());
            comment.setParentId(commentVO.getParentId());
            commentDao.insertComment(comment);
            // 当用户的评论被回复时给用户发送邮件
            if (commentVO.getReplyUserId() != 0) {
                taskExecutor.execute(() -> {
                    if (systemConfig.getUserMailReport() == SystemConfig.MailReport.START) {
                        User replyUser = userDao.selectUserById(commentVO.getReplyUserId());
                        String commentTimeStr = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
                        SendMail.sendHtmlMail(
                                mailSender,
                                systemConfig.getSmtpSender(),
                                replyUser.getEmail(),
                                "hexo blog 评论回复通知",
                                String.format("文章: %s<br>用户名: %s <br> 邮箱: %s<br> 评论时间: %s <br> 评论内容: %s <br> <a href='%s'>点击查看</a> ", article.getTitle(), user.getName(), user.getEmail(), commentTimeStr, comment.getContent(), systemConfig.getHexoVisitUrl() + article.getPath())
                        );
                    }
                });
            }
            // 用户评论后给管理员发送邮件
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            taskExecutor.execute(() -> {
                try {
                    if (systemConfig.getAdminMailReport() == SystemConfig.MailReport.START) {
                        String commentTimeStr = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
                        log.error(String.format("准备发送邮件 文章:%s \n 用户名:%s \n 邮箱: %s \n 评论时间:%s \n 评论内容:%s", article.getTitle(), user.getName(), user.getEmail(), commentTimeStr, comment.getContent()));
                        // 请求后台登录接口获取jsessionid
                        String url = String.format("http://%s:%s/admin/login", serverName, serverPort);
                        Admin admin = adminDao.selectAdminByEmail(systemConfig.getSmtpSender());
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> adminMap = new HashMap<>();
                        adminMap.put("username", admin.getUsername());
                        adminMap.put("password", AESUtil.decryptBase64(admin.getPassword(), admin.getSecret()));
                        String json = objectMapper.writeValueAsString(adminMap);
                        Response okhttpResponse = OkHttpUtil.getOkHttpClient().newCall(new Request.Builder().url(url).post(RequestBody.create(json, OkHttpUtil.JSON)).build()).execute();
                        String jsessionid = okhttpResponse.headers().get("jsessionid");
                        okhttpResponse.close();
                        // 发送邮件，点击审核超链接，可直接登录至系统审核页面，免去登录步骤
                        SendMail.sendHtmlMail(
                                mailSender,
                                systemConfig.getSmtpSender(),
                                systemConfig.getSmtpSender(),
                                "hexo blog 评论通知",
                                String.format("文章: %s<br>用户名: %s <br> 邮箱: %s<br> 评论时间: %s <br> 评论内容: %s <br> <a href='%s'>审核</a> ", article.getTitle(), user.getName(), user.getEmail(), commentTimeStr, comment.getContent(), systemConfig.getHexoAdminUrl() + "#/login?redirect=/comment/list&jsessionid=" + jsessionid)
                        );
                        log.error(String.format("邮件发送成功 文章:%s<br>用户名:%s <br> 邮箱: %s<br> 评论时间:%s <br> 评论内容:%s <br> <a href='%s'>审核</a> ", article.getTitle(), user.getName(), user.getEmail(), commentTimeStr, comment.getContent(), systemConfig.getHexoAdminUrl() + "#/login?redirect=/comment/list&jsessionid=" + jsessionid));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return new Result(Result.Code.SUCCESS);
        } else {
            return new Result(Result.Code.ERROR, "文章不存在！");
        }
    }

    /**
     * 根据id删除一条评论
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result deleteById(String id) {
        Integer i = commentDao.deleteById(id);
        if (i == 1) {
            return new Result(Result.Code.SUCCESS, "删除成功！");
        } else {
            return new Result(Result.Code.ERROR, "删除失败！");
        }
    }

    /**
     * 审核评论
     *
     * @param id
     * @param audit
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result audit(String id, int audit) {
        Integer i = commentDao.audit(id, audit);
        if (i == 1) {
            return new Result(Result.Code.SUCCESS, "操作成功！");
        } else {
            return new Result(Result.Code.ERROR, "操作失败！");
        }
    }

    /**
     * 审核多条评论
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result auditBatch(List<Map<String, Object>> param) {
        commentDao.auditBatch(param);
        return new Result(Result.Code.SUCCESS, "操作成功！");
    }

    /**
     * 批量删除评论
     *
     * @param map
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatchById(Map<String, Object> map) {
        if (map.get("id") != null && !"".equals(map.get("id"))) {
            List idList = (List) map.get("id");
            commentDao.deleteBatchById(idList);
            return new Result(Result.Code.SUCCESS, "操作成功！");
        } else {
            return new Result(Result.Code.ERROR, "参数不正确！");
        }
    }

}
