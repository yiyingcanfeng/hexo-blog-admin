package com.movefeng.hexoblogadmin.controller;

import com.movefeng.hexoblogadmin.model.SystemConfig;
import com.movefeng.hexoblogadmin.service.SystemService;
import com.movefeng.hexoblogadmin.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author z
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Resource
    private SystemService systemService;

    /**
     * 查看系统设置
     *
     * @return
     */
    @RequestMapping("listSettings")
    public Result listSettings() {
        return systemService.listSettings();
    }

    /**
     * 更新系统配置信息
     *
     * @param systemConfig
     * @return
     */
    @RequestMapping("updateSettings")
    public Result updateSettings(@RequestBody SystemConfig systemConfig) {
        return systemService.updateSettings(systemConfig);
    }

}
