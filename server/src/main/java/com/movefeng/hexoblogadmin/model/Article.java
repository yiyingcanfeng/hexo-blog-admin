package com.movefeng.hexoblogadmin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class Article {

    @EqualsAndHashCode.Exclude
    private Integer id;
    private String title;
    private String path;
    private String content;
    private Date createTime;
}
