package com.movefeng.hexoblogadmin.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private Integer id;
    private String content;
    private Date createTime;
    private Integer userId;
    private Integer articleId;
    private Integer parentId;
    private Integer replyUserId;
    private Integer auditStatus;

    public interface AuditStatus{
        int AUDIT_SUCCESS = 1;
        int WAIT_AUDIT = 0;
    }

}
