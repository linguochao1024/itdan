package com.linguochao.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;


/**
 * 前台用户网关过滤器
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Component
public class WebFilter extends ZuulFilter {

    /**
     * 过滤器类型
     *   pre: 在执行网关之前
     *   route: 在执行网关的时候
     *   post: 在执行网关之后
     *   error: 在执行网关出错的时候
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序
     *    数值越大，优先级越低！！！
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行过滤的方法run
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 执行过滤的逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        System.out.println("执行了WebFilter网关过滤器");

        //找回用户请求的Authorization信息

        //1.获取到用户请求的Authorization信息
        //1.1 获取request对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request =  requestContext.getRequest();
        //1.2 获取Authorization信息
        String auth = request.getHeader("Authorization");

        //2. 把auth信息加回到网关转发后的请求头里面去
        if(auth!=null && !auth.equals("")){
            requestContext.addZuulRequestHeader("Authorization",auth);
        }

        //return null代表放行！！
        return null;
    }
}

