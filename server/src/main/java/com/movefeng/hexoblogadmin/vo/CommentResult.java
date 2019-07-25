package com.movefeng.hexoblogadmin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author z
 */

@Data
public class CommentResult {

    private Integer commentCount;
    private List<CommentVOs> commentVOs;

}
