package com.movefeng.hexoblogadmin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"id"})
public class Article {

    private Integer id;
    private String title;
    private String content;
    private Date createTime;
}
