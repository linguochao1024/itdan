package com.linguochao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import util.IdWorker;

@SpringBootApplication
@EnableEurekaClient
public class JpaApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class,args);
    }

    /**
     * 配置跨域 允许所有请求
     *
     * @param registry
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
        super.addCorsMappings(registry);
    }


    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
