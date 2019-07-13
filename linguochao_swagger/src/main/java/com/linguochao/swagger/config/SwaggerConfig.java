package com.linguochao.swagger.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 *
 * @author linguochao
 * @date 2019\7\13 0013
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Value("${swagger2.enable}")
    private boolean swagger2Enable;


    @Bean
    public Docket RestApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .apiInfo(apiInfo())
                .groupName("消息接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linguochao.swagger.controller.massage"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket RestApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .apiInfo(apiInfo())
                .groupName("用户接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linguochao.swagger.controller.user"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("客户管理平台接口")
                .description("实时文档，可在线调试")
                .termsOfServiceUrl("http://10.0.0.254:4999/web/#/12?page_id=1825")
                .version("1.0")
                .build();
    }
}

