package com.movefeng.hexoblogadmin.config;

import com.movefeng.hexoblogadmin.config.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * 如果继承WebMvcConfigurationSupport ,那么SpringBoot默认有关Jackson的配置会失效
 * 然后Jackson框架日期输出无法按照配置的格式进行格式化，而是转化为long型的值。
 * 解决方案:
 * 1.使用注解:@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
 * 2.实现WebMvcConfigurer接口，由于jdk1.8接口有默认实现，不必继承 WebMvcConfigurerAdapter，该类已被废弃
 *
 * @author zxy
 * @see WebMvcConfigurerAdapter
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .addPathPatterns("/article/**")
                .excludePathPatterns("/admin/login");
    }

}
