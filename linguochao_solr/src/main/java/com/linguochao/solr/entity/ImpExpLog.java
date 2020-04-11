package com.linguochao.solr.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * @author linguochao
 * @Description Solr导入导出日志
 * @Date 2020/4/8 14:21
 */
@Data
@SolrDocument(solrCoreName = "ImpExpLog")
public class ImpExpLog {
    @Field
    private Long id;

    @Field("name")
    private String name;

    @Field("importtime")
    private String importTime;

    @Field("createtime ")
    private String createTime;

    @Field("content")
    private String content;

    @Field("handler")
    private String handler;

    @Field("status")
    private String status;

    @Field("amount")
    private String amount;

    @Field("failurecount")
    private String failureCount;

    @Field("logtype")
    private String logType;

    @Field("url")
    private String url;

    @Field("message")
    private String message;


}
