package com.ichirinko.luce;

/**
 * @ClassName SwaggerConfig
 * @Description
 * @Author Ichirinko
 * @Date 2024/6/1 1:47
 * @Version 1.0
 **/

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI registrationOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Luce Openapi")
                        .description("Luce的在线接口测试")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer")) // 添加安全需求
                .components(addSecuritySchemes()); // 添加安全方案;
    }

    private Components addSecuritySchemes() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT") // 如果你的token是JWT格式的，可以指定
                .name("Authorization")
                .in(SecurityScheme.In.HEADER)
                .description("Please enter JWT with Bearer into the field");

        return new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("Bearer", securityScheme);
    }
}