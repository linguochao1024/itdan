package com.linguochao.solr.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/3/24 14:20
 */
@Data
public class Person implements Serializable {

    @Field
    private Long id;

    @Field("name")
    private String name;

    @Field("departname")
    private String departName;

    @Field("departpath")
    private String departPath;

    @Field("departid")
    private Long departId;

    @Field("mobile")
    private String mobile;

    @Field("certificate")
    private String certificate;

    @Field("status")
    private String status;

    @Field("ordernumber")
    private String orderNumber;


    private String keywords;

}
