package com.movefeng.hexoblogadmin.config;

import com.movefeng.hexoblogadmin.config.interceptor.AdminInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
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
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    //@Bean
    //public AdminInterceptor adminInterceptor() {
    //    return new AdminInterceptor();
    //}

    @Resource
    AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .addPathPatterns("/article/**")
                .addPathPatterns("/user/**")
                .addPathPatterns("/comment/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/comment/create")
                .excludePathPatterns("/comment/list")
        ;
    }

    /**
     * 目前需要使用FilterRegistrationBean注册一个Filter，然后在此Filter中注入FindByIndexNameSessionRepository bean
     * AdminInterceptor中注入的FindByIndexNameSessionRepository bean才能生效，如果跳过此步，
     * 直接在AdminInterceptor中注入FindByIndexNameSessionRepository，启动时会报
     * Circular reference involving containing bean
     * 'org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration'
     * - consider declaring the factory method as static for independence from its containing instance.
     * 错误，然后启动中止，暂时没搞清楚是什么原因，
     * 我猜测可能是Interceptor、FindByIndexNameSessionRepository、FilterRegistrationBean的注入顺序导致的
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean sessionFilter() {
        FilterRegistrationBean<SessionFilter> registrationBean = new FilterRegistrationBean<>();
        SessionFilter sessionFilter = new SessionFilter(sessionRepository);
        registrationBean.setFilter(sessionFilter);

        registrationBean.addUrlPatterns(
                "/admin/*",
                "/article/*",
                "/user/*",
                "/comment/*"
        );
        return registrationBean;
    }

    /**
     * 使用下面配置后，我们可以将Spring Session默认的Cookie Key从SESSION替换为原生的JSESSIONID。
     * 而CookiePath设置为根路径且配置了相关的正则表达式，可以达到同父域下的单点登录的效果，
     * 在未涉及跨域的单点登录系统中，这是一个非常优雅的解决方案。
     * 如果我们的当前域名是moe.cnkirito.moe，该正则会将Cookie设置在父域cnkirito.moe中，
     * 如果有另一个相同父域的子域名blog.cnkirito.moe也会识别这个Cookie，便可以很方便的实现同父域下的单点登录
     *
     * @return
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        // 是否使用Base64编码cookie
        //serializer.setUseBase64Encoding(false);
        return serializer;
    }

    ///**
    // * 在header中认证session
    // *
    // * @return
    // */
    //@Bean
    //public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {
    //    return new HeaderHttpSessionIdResolver("jsessionid");
    //}
    //
    //@Bean
    //CookieHttpSessionIdResolver cookieHttpSessionIdResolver(){
    //    return new CookieHttpSessionIdResolver();
    //}

}
