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
@Table(name="tb_admin")
public class Admin implements Serializable {
    @Id
    private String id;//ID

    private String loginname;//登陆名称
    private String password;//密码
    private String state;//状态
}
