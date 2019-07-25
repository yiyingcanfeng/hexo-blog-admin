package com.movefeng.hexoblogadmin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author z
 */
@Data
public class CommentVOs {

    private CommentVO comment;
    private int childCommentCount;
    private List<CommentVO> childComment;

}
