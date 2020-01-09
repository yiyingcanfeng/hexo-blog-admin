package com.movefeng.hexoblogadmin.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movefeng.hexoblogadmin.utils.EncryptUtils;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @author z
 */
@Slf4j
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public AdminInterceptor(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * 目前针对浏览器端和一些对cookie支持有限的非浏览器端(比如Android端，部分桌面端)
     * 设计2种session认证方案，
     * 第一种，支持cookie的，比如浏览器：登录成功后，服务器端将用户的信息保存至session，并设置有效期，
     * 服务器会将sessionId放在响应头的set-cookie字段以及请求头中的JSESSIONID字段中一起返回给客户端，
     * 由于之后的请求浏览器会自动携带cookie中的sessionId，所以这个JSESSIONID的header对浏览器端的项目来说就是可有可无的了，
     * 但在部分桌面端中，比如electron，就我目前的测试来看，使用axios进行http请求时，无法设置以及获取cookie，即便是做了对跨域请求设置，
     * 这时这个JSESSIONID的header就发挥作用了，当登录成功后，客户端将header中的JSESSIONID保存至本地存储，之后的请求中，在请求头中
     * 携带JSESSIONID，然后服务器端, spring session 提供了 {@link FindByIndexNameSessionRepository} 这么一个api，
     * 通过它就可以实现根据用户名查找用户归属的SESSION的功能，然后根据客户端在请求头中传过来的JSESSIONID即可判断用户的会话是否过期。
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Enumeration<String> headerNames = request.getHeaderNames();
        String jsessionidHeader = request.getHeader("jsessionid");
        String jsessionidParam = request.getParameter("jsessionid");
        if (jsessionidHeader != null && !"".equals(jsessionidHeader)) {
            flag = validateJsessionId(response, objectMapper, jsessionidHeader);
        } else if (jsessionidParam != null && !"".equals(jsessionidParam)) {
            flag = validateJsessionId(response, objectMapper, jsessionidParam);
        } else {
            HttpSession session = request.getSession();
            Object admin = session.getAttribute("admin");
            if (admin != null) {
                flag = true;
            } else {
                Result result = new Result(Result.Code.LOGIN_INFO_EXPIRE, "登录信息过期");
                response.getWriter().write(objectMapper.writeValueAsString(result));
            }
        }
        return flag;
    }

    /**
     * 验证session是否有效
     *
     * @param response
     * @param objectMapper
     * @param jsessionid
     * @return
     * @throws IOException
     */
    private boolean validateJsessionId(HttpServletResponse response, ObjectMapper objectMapper, String jsessionid) throws IOException {
        boolean flag = false;
        Map<String, ? extends Session> sessionsMap = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, "admin");
        Set<String> keySet = sessionsMap.keySet();
        jsessionid = EncryptUtils.base64Decode(jsessionid);
        if (keySet.contains(jsessionid)) {
            flag = true;
        } else {
            Result result = new Result(Result.Code.LOGIN_INFO_EXPIRE, "登录信息过期");
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
        return flag;
    }
}
