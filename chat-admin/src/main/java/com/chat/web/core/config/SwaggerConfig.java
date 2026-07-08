package com.chat.web.core.config;

import com.chat.common.config.ChatConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

/**
 * SpringDoc OpenAPI3 配置（替代原Swagger2，适配Spring Boot3.x）
 * 功能完全对齐：文档信息 + JWT请求头认证 + 开关控制 + 路径前缀
 *
 * @author xpy
 */
@Configuration
// 配置开关：和原逻辑一致，读取swagger.enabled配置
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
public class SwaggerConfig {

    @Resource
    private ChatConfig chatConfig;

    @Value("${swagger.pathMapping:/}")
    private String pathMapping;

    /**
     * 【核心】OpenAPI 配置
     * 对齐原功能：文档标题、描述、作者、版本、Authorization请求头认证
     */
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                // 文档基础信息（1:1还原原apiInfo）
                .info(new Info()
                        .title("桔子客服管理系统_接口文档")
                        .description("用于客服聊天,具体包括XXX,XXX模块...")
                        .version(chatConfig.getVersion())
                        .contact(new Contact()
                                .name(chatConfig.getName())
                        )
                )
                // 安全配置：全局添加 Authorization 请求头（JWT Token）
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new Components()
                        .addSecuritySchemes("Authorization",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("请求头Token认证")
                        )
                );
    }
}