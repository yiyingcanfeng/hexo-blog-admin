package com.movefeng.hexoblogadmin.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author z
 */
@Slf4j
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        HttpSession session = request.getSession();
        Object admin = session.getAttribute("admin");
        if (admin != null) {
            flag = true;
        } else {
            Result result = new Result(Result.Code.LOGIN_INFO_EXPIRE,"登录信息过期");
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
        return flag;
    }
}
