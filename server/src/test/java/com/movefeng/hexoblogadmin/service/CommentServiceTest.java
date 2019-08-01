package com.movefeng.hexoblogadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.dao.CommentDao;
import com.movefeng.hexoblogadmin.model.Comment;
import com.movefeng.hexoblogadmin.model.User;
import com.movefeng.hexoblogadmin.vo.ArticleVO;
import com.movefeng.hexoblogadmin.vo.CommentVO;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author z
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CommentServiceTest {

    @Resource
    private CommentService commentService;
    @Resource
    private ArticleService articleService;
    @Resource
    private UserService userService;
    @Resource
    private CommentDao commentDao;

    @Test
    public void createComment() throws JsonProcessingException {
        CommentVO commentVO = new CommentVO();
        commentVO.setArticleTitle("学习Java.01");
        commentVO.setUserMail("bbb@qq.com");
        commentVO.setUsername("李四");
        commentVO.setContent("是的呢");
        commentVO.setReplyUserId(1);
        commentVO.setParentId(1);
        Result comment = commentService.createComment(commentVO);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(comment);

        log.error(s);

    }

    @Test
    public void queryComment() throws JsonProcessingException {
        String articleTitle = "学习Java.01";
        Result result = commentService.queryComment(articleTitle);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(result);
        log.error(s);

    }

    /**
     * 生成测试的评论数据
     */
    @Test
    public void generateComment() {
        Page<ArticleVO> articleVOS = articleService.queryArticle(new HashMap<>());
        Page<User> userList = userService.userList(new HashMap<>());
        List<String> contentList = Arrays.asList("666", "写的不错", "非常好的文章", "写的真棒", "收藏了");

        for (int i = 0; i < 30; i++) {

            Random random = new Random();
            ArticleVO articleVO = articleVOS.get(random.nextInt(articleVOS.size()));
            User user = userList.get(random.nextInt(userList.size()));

            Comment comment = new Comment();
            comment.setUserId(user.getId());
            comment.setArticleId(articleVO.getId());
            comment.setContent(contentList.get(random.nextInt(contentList.size())));
            comment.setCreateTime(new Date(System.currentTimeMillis()-random.nextInt(1000*86400)));
            comment.setParentId(0);
            comment.setReplyUserId(0);

            commentDao.insertComment(comment);

        }

    }
}