package com.linguochao.solr.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/3/24 11:19
 */
@RestController
public class SolrController {

    @RequestMapping("/")
    public String testSolr(){
        return null;
    }

}
