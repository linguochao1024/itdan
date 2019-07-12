package com.linguochao.jpa.pojo;

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
@Table(name = "tb_label")
public class Label implements Serializable {

    @Id
    private String id;

    private String labelname;
    private String state;
    private Long count;
    private String recommend;
    private Long fans;
}
