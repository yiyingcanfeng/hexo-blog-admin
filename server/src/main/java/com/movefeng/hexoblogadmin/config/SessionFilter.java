package com.movefeng.hexoblogadmin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author z
 */
@Slf4j
public class SessionFilter implements Filter {

    //@Resource
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public SessionFilter(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //HttpServletResponse response = (HttpServletResponse) res;
        //HttpServletRequest request = (HttpServletRequest) req;
        //
        //response.setContentType("application/json;charset=UTF-8");
        //ObjectMapper objectMapper = new ObjectMapper();
        //
        //Map<String, ? extends Session> sessionsMap = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, "admin");
        //Set<String> keySet = sessionsMap.keySet();
        //log.info(keySet+","+sessionRepository.hashCode());
        //
        //List<String> excludeList = Arrays.asList("/admin/login","/comment/create","/comment/list");
        //String requestUri = request.getRequestURI();
        //if (!excludeList.contains(requestUri)) {
        //
        //    String jsessionid = request.getHeader("jsessionid");
        //    if (jsessionid != null && !"".equals(jsessionid)) {
        //        Map<String, ? extends Session> sessionsMap = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, "admin");
        //        Set<String> keySet = sessionsMap.keySet();
        //
        //        if (!keySet.contains(jsessionid)) {
        //            Result result = new Result(Result.Code.LOGIN_INFO_EXPIRE, "登录信息过期");
        //            response.getWriter().write(objectMapper.writeValueAsString(result));
        //        }
        //    } else {
        //        HttpSession session = request.getSession();
        //        Object admin = session.getAttribute("admin");
        //        if (admin == null) {
        //            Result result = new Result(Result.Code.LOGIN_INFO_EXPIRE, "登录信息过期");
        //            response.getWriter().write(objectMapper.writeValueAsString(result));
        //        }
        //    }
        //}
        chain.doFilter(req, res);
    }
}
