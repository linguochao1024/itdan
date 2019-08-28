package com.linguochao.controller;

import com.linguochao.annotation.SysLog;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author linguochao
 * @Description 方法通过注解+AOP实现自动收集日志
 * @Date 2019/7/21 16:02
 */
public class SysUserController {

    @SysLog("修改用户")
    @PutMapping("/update")
    public SysUserController update(@RequestBody SysUserController user){

        return user;
    }
}
