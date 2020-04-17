package com.linguochao.annotation;

import java.lang.annotation.*;

/**
 * @author linguochao
 * @Description 多数据源注解
 * @Date 2019/7/21 15:42
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
