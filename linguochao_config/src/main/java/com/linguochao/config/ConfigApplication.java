package com.linguochao.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 中心微服务
 *
 * @author linguochao
 * @date 2019\6\30 0030
 */
@SpringBootApplication
@EnableConfigServer  //开启配置中心
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class,args);
    }
}
