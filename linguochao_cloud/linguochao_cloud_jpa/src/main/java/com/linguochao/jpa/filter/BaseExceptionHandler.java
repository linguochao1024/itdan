package com.linguochao.jpa.filter;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 处理方法
     */
    /*@ExceptionHandler(value = NullPointerException.class)  // 只能处理空指针异常
    public Result error(){

    }*/


    @ExceptionHandler(value = Exception.class)  // 处理所有异常
    @ResponseBody
    public Result error(Exception e){
        return new Result(false, StatusCode.ERROR,"操作失败："+e.getMessage());
    }

}
