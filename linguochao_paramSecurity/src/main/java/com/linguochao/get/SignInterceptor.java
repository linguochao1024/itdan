package com.linguochao.get;

import com.linguochao.post.ApiHttpInputMessage;
import com.linguochao.utils.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linguochao
 * @Description 签名拦截器
 * @Date 2019/8/15 15:53
 */
@Component
@Slf4j
public class SignInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (request.getMethod().equals("GET")&&request.getQueryString()!=null) {
            //获取url的参数
            Map<String, String> queryString = getQueryString(request);
            String sign = request.getHeader("sign");
            String nonce = request.getHeader("nonce");
            //其他参数拼接
            String queryParam = "";
            for (String key : queryString.keySet()) {
                queryParam += queryString.get(key);
            }
            ApiHttpInputMessage.verifySign(sign, nonce, queryParam, AESUtils.KEY);
        }
        log.info("签名验证通过");
        return true;
    }

    private Map<String, String> getQueryString(HttpServletRequest request) {
        Map<String, String> queryStringMap = new HashMap<>();
        //获取请求数据
        String queryString = request.getQueryString();
        log.info("queryString" + queryString);
        for (String kv : queryString.split("&")) {
            int charIndex = kv.indexOf("=");
            queryStringMap.put(kv.substring(0, charIndex), kv.substring(charIndex));
        }
        return queryStringMap;
    }
    private Map<String, String> getHeader(HttpServletRequest request) {
        Map<String, String> queryStringMap = new HashMap<>();
        return queryStringMap;
    }
}

