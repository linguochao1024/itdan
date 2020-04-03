package com.linguochao.solr.entity;

import lombok.Data;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/4/3 16:01
 */
@Data
public class SearchParams {

    private String keywords;

    private Integer isAll;

    private Integer status;

    private Integer departId;

    private String departmentPath;
}
