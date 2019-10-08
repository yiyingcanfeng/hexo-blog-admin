package com.movefeng.hexoblogadmin.service;

import com.movefeng.hexoblogadmin.dao.SystemConfigDao;
import com.movefeng.hexoblogadmin.model.SystemConfig;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author z
 */
@Slf4j
@Service
public class SystemService {

    @Resource
    private SystemConfigDao systemConfigDao;

    @Resource
    private SystemConfig systemConfig;

    /**
     * 更新系统配置信息
     *
     * @param systemConfig
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result updateSettings(SystemConfig systemConfig) {
        String hexoVisitUrl = systemConfig.getHexoVisitUrl();
        if (hexoVisitUrl.lastIndexOf("/") != hexoVisitUrl.length() - 1 || (!hexoVisitUrl.startsWith("http://") && !hexoVisitUrl.startsWith("https://"))) {
            return new Result(Result.Code.ERROR, "url输入不正确！");
        }
        BeanUtils.copyProperties(systemConfig, this.systemConfig);
        systemConfigDao.update(systemConfig);
        return new Result(Result.Code.SUCCESS);
    }

    /**
     * 查询系统设置
     *
     * @return
     */
    public Result listSettings() {
        SystemConfig systemConfig = systemConfigDao.select();
        return new Result<>(Result.Code.SUCCESS, systemConfig);
    }
}
