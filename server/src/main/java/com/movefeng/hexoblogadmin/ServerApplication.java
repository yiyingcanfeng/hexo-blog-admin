package com.movefeng.hexoblogadmin;

import com.movefeng.hexoblogadmin.config.CustomProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import java.util.HashSet;
import java.util.Set;

@EnableConfigurationProperties({ CustomProperties.class })
@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServerApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        Set<SessionTrackingMode> set = new HashSet<>();
        set.add(SessionTrackingMode.COOKIE);
        set.add(SessionTrackingMode.URL);
        servletContext.setSessionTrackingModes(set);
        SessionCookieConfig sessionCookieConfig=servletContext.getSessionCookieConfig();
        sessionCookieConfig.setHttpOnly(true);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
