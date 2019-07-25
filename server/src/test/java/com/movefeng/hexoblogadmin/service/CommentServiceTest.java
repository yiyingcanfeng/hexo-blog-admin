package com.movefeng.hexoblogadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movefeng.hexoblogadmin.vo.CommentVO;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author z
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CommentServiceTest {

    @Resource
    private CommentService commentService;

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
}