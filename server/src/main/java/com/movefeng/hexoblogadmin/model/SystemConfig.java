package com.movefeng.hexoblogadmin.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 全局配置信息
 *
 * @author z
 */
@Data
@Component
public class SystemConfig {

    /**
     * 文章目录
     */
    private String articlePath;

    /**
     * hexo目录
     */
    private String hexoPath;

    /**
     * 博客的发布目录
     */
    private String publicPath;

    /**
     * smtp邮箱
     */
    private String smtpEmail;
}
