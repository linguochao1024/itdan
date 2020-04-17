package com.linguochao.solr.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.util.Map;

/**
 * @author linguochao
 * @Description Solr人员数据
 * @Date 2020/3/24 14:20
 */
@Data
@SolrDocument(solrCoreName = "addressbookperson")
public class Person implements Serializable {

    @Field
    private Long id;

    @Field("name")
    private String name;

    @Field("account")
    private String account;

    @Field("mobile")
    private String mobile;

    @Field("certificate")
    private String certificate;

    @Field("status")
    private String status;


    @Field("departname")
    private String[] departName;

    @Field("departfullname")
    private String[] departFullName;

    @Field("departpath")
    private String[] departPath;

    @Field("departid")
    private Long[] departId;

    @Dynamic
    @Field("ordernumber_*")
    private Map<String,Long> ordernumberMap;

    private Long ordernumber;
}
