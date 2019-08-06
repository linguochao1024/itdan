package com.linguochao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author linguochao
 * @Description Excel属性注解
 * @Date 2019/8/6 12:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAttribute {

    /** 对应的列名称 */
    String name() default "";

    /** 列序号 */
    int sort();

    /** 字段类型对应的格式 */
    String format() default "";
}
