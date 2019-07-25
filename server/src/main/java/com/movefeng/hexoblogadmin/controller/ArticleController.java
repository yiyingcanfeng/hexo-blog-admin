package com.movefeng.hexoblogadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movefeng.hexoblogadmin.model.Article;
import com.movefeng.hexoblogadmin.service.ArticleService;
import com.movefeng.hexoblogadmin.service.CommentService;
import com.movefeng.hexoblogadmin.vo.ArticleVO;
import com.movefeng.hexoblogadmin.vo.CommentVO;
import com.movefeng.hexoblogadmin.vo.Result;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author z
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

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

    @RequestMapping("content")
    public Result content(String title) {
        return articleService.getArticleContent(title);
    }

    @RequestMapping("save")
    public Result save(@RequestBody Map<String, String> map) {
        String oldTitle = map.get("oldTitle");
        String title = map.get("title");
        String content = map.get("content");
        return articleService.saveArticle(oldTitle, title, content);
    }

    @RequestMapping("add")
    public Result add(@RequestBody Map<String, String> map) {
        String title = map.get("title");
        String content = map.get("content");
        return articleService.createArticle(title, content);
    }

    @RequestMapping("delete")
    public Result delete(@RequestBody Map<String, String> map) {
        String title = map.get("title");
        return articleService.deleteArticle(title);
    }

    @RequestMapping("generate")
    public Result generate() {
        return articleService.generate();
    }


}
