package com.linguochao.feign.intercepter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Jwt验证拦截器
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;


    /**
     * preHandle：在Controller方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、 使用jjwt解析token字符串，是否合法（是否过期）
        //1.1 获取Authorization信息
        String auth = request.getHeader("Authorization");
        if(auth!=null){
            //1.2 判断是否以Bearer开头
            if(auth.startsWith("Bearer")){
                //1.3 获取token 字符串
                String token = auth.substring(7);

                //1.4 使用jjwt工具校验token是否合法
                Claims claims = jwtUtil.parseJWT(token);

                //1.5 从token字符串的载荷取出roles
                if("admin".equals(claims.get("roles") )){
                    //2 、从载荷获取roles，如果roles是管理员
                    //往request存入标记
                    request.setAttribute("admin_claims",claims);
                }

                if("user".equals(claims.get("roles") )){
                    //2 、从载荷获取roles，如果roles是普通用户
                    //往request存入标记
                    request.setAttribute("user_claims",claims);
                }

            }

        }

        //放行
        return true;
    }
}

