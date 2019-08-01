package com.movefeng.hexoblogadmin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movefeng.hexoblogadmin.model.Admin;
import com.movefeng.hexoblogadmin.service.AdminService;
import com.movefeng.hexoblogadmin.utils.EncryptUtils;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author z
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Resource
    private HttpSession session;

    @Resource
    private AdminService adminService;

    @PostMapping("login")
    public Result list(@RequestBody Admin admin) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(admin, new TypeReference<Map<String, Object>>() {
        });
        map.put("token", "admin-token");
        map.remove("password");
        boolean b = adminService.adminLogin(admin);
        if (b) {
            session.setAttribute("admin", "admin");
            // 目前暂时将value写死
            session.setAttribute(
                    FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, "admin");
            response.addHeader("jsessionid", EncryptUtils.base64Encode(session.getId()));
            //session有效期：1小时
            session.setMaxInactiveInterval(60 * 60);
            return new Result<>(Result.Code.SUCCESS, map);
        } else {
            return new Result<>(Result.Code.LOGIN_INFO_INCORRECT, "用户名或密码错误！");
        }

    }

    @RequestMapping("info")
    public Result info(Admin admin) {
        Admin adminInfo = adminService.adminInfo(admin);
        return new Result<>(Result.Code.SUCCESS, adminInfo);
    }

    @RequestMapping("logout")
    public Result logout() {
        session.invalidate();
        return new Result<>(Result.Code.SUCCESS, "success");
    }


    @RequestMapping("analysis")
    public Result analysis(Admin admin) {

        Admin adminInfo = adminService.adminInfo(admin);
        return new Result<>(Result.Code.SUCCESS, adminInfo);
    }

}
