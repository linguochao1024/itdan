package com.linguochao.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka微服务
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@SpringBootApplication
@EnableEurekaServer  // 开启Eureka服务
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }

}
