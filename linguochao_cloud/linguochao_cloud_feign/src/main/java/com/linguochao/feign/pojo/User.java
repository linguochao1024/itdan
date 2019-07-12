package com.linguochao.feign.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name="tb_user")
public class User implements Serializable {

    @Id
    private String id;//ID

    private String mobile;//手机号码
    private String password;//密码
    private String nickname;//昵称
    private String sex;//性别
    private java.util.Date birthday;//出生年月日
    private String avatar;//头像
    private String email;//E-Mail
    private java.util.Date regdate;//注册日期
    private java.util.Date updatedate;//修改日期
    private java.util.Date lastdate;//最后登陆日期
    private Long online;//在线时长（分钟）
    private String interest;//兴趣
    private String personality;//个性
    private Integer fanscount;//粉丝数
    private Integer followcount;//关注数
}
