package com.linguochao.exception;

import lombok.Data;


/**
 * @author linguochao
 * @Description api接口异常类
 * @Date 2019/8/28 17:08
 */
@Data
public class ApiException extends RuntimeException {
    private String code;
    private String message;

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApiException(SystemException.ExceptionHolder exceptionHolder) {
        this(exceptionHolder.code, exceptionHolder.message);
    }
}
