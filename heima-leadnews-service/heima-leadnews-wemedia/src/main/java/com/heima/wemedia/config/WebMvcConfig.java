package com.heima.wemedia.config;

import com.heima.wemedia.interceptor.WmTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private WmTokenInterceptor wmTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(wmTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/in");
    }
}
