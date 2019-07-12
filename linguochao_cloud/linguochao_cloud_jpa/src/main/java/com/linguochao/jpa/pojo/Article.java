package com.linguochao.feign.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(type = "article",indexName = "linguocaho")
public class Article implements Serializable {

    @Id
    private String id;//ID

    //@Field
    private String columnid;//专栏ID
    private String userid;//用户ID
    private String title;//标题
    private String content;//文章正文
    private String image;//文章封面
    private java.util.Date createtime;//发表日期
    private java.util.Date updatetime;//修改日期
    private String ispublic;//是否公开
    private String istop;//是否置顶
    private Integer visits;//浏览量
    private Integer thumbup;//点赞数
    private Integer comment;//评论数
    private String state;//审核状态
    private String channelid;//所属频道
    private String url;//URL
    private String type;//类型
}
