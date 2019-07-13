package com.linguochao.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * description
 *
 * @author linguochao
 * @date 2019\7\13 0013
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "消息", description = "消息详情")
public class Message {
    @ApiModelProperty(value = "消息id")
    private Long id;
    @ApiModelProperty(value = "消息体")
    private String text;
    @ApiModelProperty(value = "消息总结")
    private String summary;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
}

