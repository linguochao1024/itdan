package com.linguochao.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linguochao
 * @Description 系统异常类
 * @Date 2019/8/28 17:09
 */

@Data
@NoArgsConstructor
public class SystemException extends Exception {

    private String code;

    public SystemException(String message){
        super(message);
        this.code = "-1";
    }

    public SystemException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(ExceptionHolder holder) {
        super(holder.message);
        this.code = holder.code;
    }

    /**
     * 异常枚举
     * 枚举所有的自定义错误码的异常
     */
    public enum ExceptionHolder {

        /**
         * 账号不存在
         */
        LOGIN_ERROR_USER_IS_NULL("101","账号不存在"),

        /**
         * 密码不正确
         */
        PASSWORD_ERR("102","密码不正确"),

        /**
         * 该帐号未审核
         */
        NOT_AUTHED("103","该帐号未审核"),

        /**
         * 账号冻结
         */
        FROZEN("104", "该账号被冻结"),

        /**
         * 没有操作权限
         */
        NO_PERMISSION("201", "没有操作权限"),

        /**
         * 没有登录
         */
        NO_LOGIN("202", "没有登录，请先登录"),

        /**
         * 未绑定管理者手机
         */
        NO_MOBILE("203", "未绑定手机"),

        /**
         * 参数缺失
         */
        BODY_PARAM_MISS("211", "参数缺失"),

        /**
         * 签名缺失
         */
        SIGN_MISS("212", "签名缺失"),

        /**
         * 随机串缺失
         */
        NONCE_MISS("213", "随机串缺失"),

        /**
         * 签名错误
         */
        SIGN_WRONG("214", "签名错误"),

        /*
         * 加密错误
         */
        ENCRYPT_ERROR("215", "加密错误"),

        /**
         * 加密错误
         */
        DECRYPT_ERROR("216", "解密错误"),


        /**
         * 验证码已过期
         */
        CAPTCHA_LOST("304", "验证码已过期，请重新获取"),

        /**
         * 验证码不正确
         */
        CAPTCHA_ERROR("305", "验证码不正确，请重新输入"),


        /**
         * 导出excel文件出错
         */
        ERROR_EXPORT_EXCEL("502","导出excel文件出错"),

        /**
         * 暂无数据导出
         */
        NOT_DATA_EXPORT("503","暂无相关数据");


        public String code;

        public String message;

        ExceptionHolder(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}

