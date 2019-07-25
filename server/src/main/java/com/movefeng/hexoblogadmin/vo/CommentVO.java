package com.movefeng.hexoblogadmin.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author z
 */

@Data
public class CommentVO {

    private Integer id;
    private String content;
    private Date createTime;
    private Integer userId;
    private String username;
    private String userMail;
    private String userWebsite;
    private String articleTitle;
    private Integer parentId;
    private Integer replyUserId;
    private String replyUserName;
    private Integer auditStatus;

}
