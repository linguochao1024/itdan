package com.linguochao.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;
import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理员网关过滤器
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Component
public class ManageFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Object run() throws ZuulException {

        //1.取出Authorization信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String auth = request.getHeader("Authorization");

        //单独对某些请求来放行
        String uri = request.getRequestURI();
        if(uri.contains("/admin/login")){
            //放行
            return null;
        }

        //2.判断Authorization信息是否存在
        if(auth!=null && !auth.equals("")){
            //3.判断是否以Bearer开头
            if(auth.startsWith("Bearer")){
                //4.取出token字符串
                String token = auth.substring(7);

                //5.解析token是否合法
                Claims claims = jwtUtil.parseJWT(token);

                //6.判断当前用户是否为管理员
                if( "admin".equals(claims.get("roles"))  ){

                    //放行，继续访问微服务
                    return null;
                }
            }
        }


        //不是管理员，不能放行，并且提示前端信息
        //中止请求（不能放行）
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(401);	//http 状态码
        //设置响应内容
        requestContext.setResponseBody("你不是管理员，无权访问");
        //设置编码
        requestContext.getResponse().setContentType("text/html;charset=utf-8");

        return null;
    }
}

