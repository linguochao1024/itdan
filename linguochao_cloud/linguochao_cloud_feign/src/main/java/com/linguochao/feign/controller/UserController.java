package com.linguochao.feign.controller;

import com.linguochao.feign.pojo.User;
import com.linguochao.feign.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取手机验证码
     */
    @RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
    public Result sendsms(@PathVariable String mobile){
        userService.sendsms(mobile);
        return new Result(true, StatusCode.OK,"验证码发送成功");
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
    public Result register(@PathVariable String code,@RequestBody User user){
        Boolean flag = userService.register(code,user);

        if(flag){
            return new Result(true,StatusCode.OK,"注册成功");
        }else{
            return new Result(false,StatusCode.ERROR,"输入的验证码有误");
        }
    }
}
