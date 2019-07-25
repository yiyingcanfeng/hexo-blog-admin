package com.movefeng.hexoblogadmin.config.initRunner;

import com.movefeng.hexoblogadmin.dao.SystemConfigDao;
import com.movefeng.hexoblogadmin.model.SystemConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化全局系统参数
 *
 * @author z
 */
@Order(1)
@Component
public class SystemConfigInitRunner implements ApplicationRunner {

    @Resource
    private SystemConfigDao systemConfigDao;

    @Resource
    private SystemConfig systemConfig;

    @Override
    public void run(ApplicationArguments args) {
        SystemConfig config = systemConfigDao.select();
        systemConfig.setArticlePath(config.getArticlePath());
        systemConfig.setHexoPath(config.getHexoPath());
        systemConfig.setPublicPath(config.getPublicPath());
    }
}
