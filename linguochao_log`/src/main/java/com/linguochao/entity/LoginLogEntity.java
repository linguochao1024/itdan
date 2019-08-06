package com.linguochao.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author linguochao
 * @Description 登录系统日志
 * @Date 2019/7/21 16:18
 */
public class LoginLogEntity {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1")
    private String id;
    /**
     * 日志名称
     */
    @ApiModelProperty(value = "日志名称", example = "测试日志名称001")
    private String logname;

    @ApiModelProperty(value = "用户名ID", example = "xx")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "xx")
    private String username;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-08-08")
    private Date createtime;
    /**
     * 是否执行成功
     */
    @ApiModelProperty(value = "是否执行成功", example = "测试是否执行成功001")

    private String succeed;
    /**
     * 具体消息
     */
    @ApiModelProperty(value = "具体消息", example = "测试具体消息001")
    private String message;
    /**
     * 登录ip
     */
    @ApiModelProperty(value = "登录ip", example = "测试登录ip001")
    private String ip;
}
