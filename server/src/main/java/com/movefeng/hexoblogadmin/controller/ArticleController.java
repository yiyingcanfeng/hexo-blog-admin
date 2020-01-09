package com.movefeng.hexoblogadmin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movefeng.hexoblogadmin.model.Article;
import com.movefeng.hexoblogadmin.service.ArticleService;
import com.movefeng.hexoblogadmin.vo.ArticleVO;
import com.movefeng.hexoblogadmin.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author z
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 列出文章
     *
     * @param pageNum
     * @param pageSize
     * @param searchParam
     * @return
     */
    @RequestMapping("list")
    public Result list(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestBody(required = false) Map<String, Object> searchParam) {
        PageHelper.startPage(pageNum, pageSize);
        if (searchParam == null) {
            searchParam = new HashMap<>();
        }
        Page<ArticleVO> page = articleService.queryArticle(searchParam);
        PageInfo<ArticleVO> pageInfo = new PageInfo<>(page);
        return new Result<>(Result.Code.SUCCESS, pageInfo);
    }

    /**
     * 获取文章内容
     *
     * @param title
     * @return
     */
    @RequestMapping("content")
    public Result content(String title) {
        return articleService.getArticleContent(title);
    }

    /**
     * 保存文章
     *
     * @param map
     * @return
     */
    @RequestMapping("save")
    public Result save(@RequestBody Map<String, String> map) {
        String oldTitle = map.get("oldTitle");
        String title = map.get("title");
        String content = map.get("content");
        return articleService.saveArticle(oldTitle, title, content);
    }

    /**
     * 新增文章
     *
     * @param map
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody Map<String, String> map) {
        String title = map.get("title");
        String content = map.get("content");
        return articleService.createArticle(title, content);
    }

    /**
     * 删除文章
     *
     * @param map
     * @return
     */
    @RequestMapping("delete")
    public Result delete(@RequestBody Map<String, String> map) {
        String title = map.get("title");
        return articleService.deleteArticle(title);
    }

    /**
     * 生成文章
     *
     * @return
     */
    @RequestMapping("generate")
    public Result generate() {
        return articleService.generate();
    }

    /**
     * 更新文章基本信息
     *
     * @param articleVO
     * @return
     */
    @RequestMapping("update")
    public Result update(@RequestBody ArticleVO articleVO) {
        return articleService.update(articleVO);
    }

    /**
     * 记录访问信息
     *
     * @return
     */
    @RequestMapping("visitInfo")
    public String recordVisitInfo() {
        return articleService.recordVisitInfo();
    }

    /**
     * 记录文章信息
     *
     * @return
     */
    @RequestMapping("allArticleInfo")
    public void allArticleInfo(@RequestBody List<Article> articleList) {
        articleService.saveAllArticleInfo(articleList);
    }

}
