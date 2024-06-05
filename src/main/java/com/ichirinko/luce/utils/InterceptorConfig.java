package com.ichirinko.luce.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName InterceptorConfig
 * @Description
 * @Author Ichirinko
 * @Date 2024/6/1 21:33
 * @Version 1.0
 **/

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register");  // 所有用户都放行登录接口
    }
}

