package com.linguochao.controller;

import com.linguochao.pojo.Admin;
import com.linguochao.service.AdminService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){

        Claims claims=(Claims) request.getAttribute("admin_claims");
        if(claims==null){
            return new Result(true,StatusCode.ACCESS_ERROR," 无权访问");
        }
        adminService.deleteById(id);
        return new Result(true,StatusCode.OK," 删除成功");
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Admin admin){

        Admin loginAdmin = adminService.login(admin);

        if(loginAdmin==null){
            //登录失败
            return new Result(false, StatusCode.USER_PASS_ERROR,"用户名或密码输入有误");
        }else{

            //1、利用jjwt生成token加密字符串 （非标准载荷声明roles）
            String token = jwtUtil.createJWT(loginAdmin.getId(), loginAdmin.getLoginname(), "admin");

            //2、直接把token字符串返回前端
            Map data = new HashMap();
            data.put("name",loginAdmin.getLoginname());
            data.put("token",token);
            data.put("id",loginAdmin.getId());

            //登录成功
            return new Result(true,StatusCode.OK,"登录成功",data);
        }
    }

}
