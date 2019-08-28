package com.linguochao.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author linguochao
 * @Description 对请求的参数数据进行解密
 * @Date 2019/8/28 17:05
 */
@ControllerAdvice
public class ApiDecryptRequestAdvice implements RequestBodyAdvice {

    @Value("${app.is.decrypt}")
    int isDecrypt;

    private static final Logger logger = LoggerFactory.getLogger(ApiDecryptRequestAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {

        return true;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {

        //是否解密
        if (isDecrypt==0){
            List<String> list = httpInputMessage.getHeaders().get("IsCrypt");
            if ((list != null) && (list.size() > 0) && (Integer.valueOf(list.get(0)) == 0)){
                return httpInputMessage;
            }
        }
        // 对请求的参数进行解密
        logger.info("对方法：【"+ methodParameter.getMethod() +"】请求的参数进行解密");
        return new ApiHttpInputMessage(httpInputMessage);
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
