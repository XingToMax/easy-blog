package org.nuaa.tomax.easyblog.configuration;

import org.nuaa.tomax.easyblog.interceptors.SessionHandlerInterceptors;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/3/3 19:48
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionHandlerInterceptors()).addPathPatterns("/admin/**");
    }
}
