package com.linguochao.annotation;

/**
 * @author linguochao
 * @Description 日志类型枚举
 * @Date 2019/7/21 15:59
 */
public enum LogType {

    LOGIN("系统登录日志"),
    LOGIN_FAIL("系统登录失败日志"),
    EXIT("系统退出日志"),
    EXIT_FAIL("系统退出失败日志"),


    SCAN_LOGIN("扫码登录日志"),
    SCAN_LOGIN_FAIL("扫码登录失败日志"),


    EXCEPTION("异常日志"),
    BUSSINESS("业务日志"),
    SUCCESS("成功"),
    FAIL("失败");

    String message;

    LogType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

