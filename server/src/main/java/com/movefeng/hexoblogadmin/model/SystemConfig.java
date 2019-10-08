package com.movefeng.hexoblogadmin.model;

import lombok.Data;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 全局配置信息
 *
 * @author z
 */
@Data
@Component
@Order(3)
public class SystemConfig {

    /**
     * hexo的访问url,例: http://127.0.0.1/blog/
     */
    private String hexoVisitUrl;

    /**
     * 博客后台管理的访问url，例: http://127.0.0.1/admin/
     */
    private String hexoAdminUrl;

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
     * 默认发信人
     */
    private String smtpSender;

    /**
     * 是否启用管理员邮件通知(比如用户发表评论时) 1:启用 0:不启用
     */
    private Integer adminMailReport;

    /**
     * 是否启用用户邮件通知(比如用户评论审核通过、评论被回复时) 1:启用 0:不启用
     */
    private Integer userMailReport;

    public interface MailReport {
        int START = 1;
        int STOP = 0;
    }

}
