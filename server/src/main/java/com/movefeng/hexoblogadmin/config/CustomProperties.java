package com.movefeng.hexoblogadmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author z
 */
@Data
@ConfigurationProperties(prefix = "config")
public class CustomProperties {

    private String corsDomain;
    private String uploadPath;

}
