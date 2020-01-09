package com.movefeng.hexoblogadmin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class Article {

    private String content;
    private Date createTime;
    private String title;

    @EqualsAndHashCode.Exclude
    private Integer id;
    @EqualsAndHashCode.Exclude
    private String path;
    @EqualsAndHashCode.Exclude
    private Date updateTime;
    @EqualsAndHashCode.Exclude
    private Integer visitCount;
}
