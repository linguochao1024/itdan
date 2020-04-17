package com.linguochao.solr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/3/24 11:16
 */
@SpringBootApplication
@EnableAutoConfiguration
public class SolrApplication {
    public static void main(String[] args) {
        SpringApplication.run(SolrApplication.class, args);
    }
}
