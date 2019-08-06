package com.linguochao.annotation;

import java.lang.annotation.*;

/**
 * @author linguochao
 * @Description 系统日志注解
 * @Date 2019/7/21 15:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";

    LogType type() default LogType.BUSSINESS;
}
