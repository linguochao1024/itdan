package com.linguochao.solr.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * @author linguochao
 * @Description Solr接口调用日志,管理员日志
 * @Date 2020/4/8 15:11
 */
@Data
@SolrDocument(solrCoreName = "HandleLog")
public class HandleLog {

    @Field
    private Long id;

    @Field("time")
    private String logTime;

    @Field("content")
    private String content;

    @Field("handler")
    private String handler;

    @Field("sessionid")
    private String logSessionId;


}
