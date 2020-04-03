package com.linguochao.solr.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/3/24 11:19
 */
@RestController
public class SolrController {
    @Autowired
    private SolrClient client;

    @RequestMapping("/")
    public String testSolr() throws IOException, SolrServerException {
        SolrDocument document = client.getById("test", "fe7a5124-d75b-40b2-93fe-5555512ea6d2");
        System.out.println(document);
        return document.toString();
    }

}
