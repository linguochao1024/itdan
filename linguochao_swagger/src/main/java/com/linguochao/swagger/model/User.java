package com.linguochao.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description
 *
 * @author linguochao
 * @date 2019\7\13 0013
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户", description = "用户信息")
public class User {
    @ApiModelProperty(value = "用户id")
    private Long id;
    @ApiModelProperty(value = "用户姓名")
    private String name;
    @ApiModelProperty(value = "用户年龄")
    private int age;
}
