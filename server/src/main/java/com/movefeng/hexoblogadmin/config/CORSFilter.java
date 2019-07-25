package com.movefeng.hexoblogadmin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author z
 */
@Component
@Slf4j
public class CORSFilter implements Filter {

    @Value("${config.corsDomain}")
    private String corsDomain;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        log.info(String.format("访问信息ip:%s,uri:%s,port:%s", req.getRemoteAddr(), request.getRequestURI(), req.getRemotePort()));
        response.setHeader("Access-Control-Allow-Origin", corsDomain);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token, login-token");
        chain.doFilter(req, res);
    }

}