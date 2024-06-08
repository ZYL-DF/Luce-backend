package com.ichirinko.luce.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射static路径的请求到static目录下
        // 静态资源访问路径和存放路径配置
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // swagger访问配置
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/");
        //通过image访问本地的图片
        registry.addResourceHandler("/image/**").addResourceLocations("file:D:/luce/videos/");
        registry.addResourceHandler("/play/**").addResourceLocations("file:D:/luce/videos/");
    }
}

