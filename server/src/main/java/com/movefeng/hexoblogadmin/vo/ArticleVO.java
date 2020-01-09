package com.movefeng.hexoblogadmin.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"id"})
public class ArticleVO {

    private Integer id;
    private String title;
    private String path;
    private String content;
    private Date createTime;
    private Integer visitCount;
    private int commentCount;
}
