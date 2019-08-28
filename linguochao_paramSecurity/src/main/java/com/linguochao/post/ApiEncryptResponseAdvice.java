package com.linguochao.post;

import com.linguochao.exception.ApiException;
import com.linguochao.exception.SystemException;
import com.linguochao.utils.AESUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author linguochao
 * @Description 对请求响应的json数据进行加密
 * @Date 2019/8/28 17:12
 */
@ControllerAdvice
public class ApiEncryptResponseAdvice implements ResponseBodyAdvice<Object> {

    @Value("${app.is.encrypt}")
    int isEncrypt;

    private static final Logger log = LoggerFactory.getLogger(ApiEncryptResponseAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Class[] cla = methodParameter.getMethod().getParameterTypes();

        String name = methodParameter.getMethod().getName();
        log.info("加密方法名称" + name);
        log.info("Class[]"+cla);

        //swagger相关方法不加密
        if (name.equals("swaggerResources") || name.equals("apiSorts") || name.equals("getDocumentation")) {
            return false;
        }

        if (cla != null && cla.length > 0) {
            for (Class c : cla) {
                if (c.equals(MultipartFile.class)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 响应返回数据进行加密
     *
     * @param body
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public String beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        String str;
        str = JSONObject.toJSONString(body);


        //是否加密
        if (isEncrypt == 0 ) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) serverHttpRequest;
            //获取请求头,判断是否加解密
            List<String> list = serverRequest.getHeaders().get("IsCrypt");
            if (list!=null &&list.size()>0 && Integer.valueOf(list.get(0))== 0){
                return str;
            }
        }

        try {
            log.info("对方法：【" + methodParameter.getMethod() + "】返回的数据进行加密");
            log.info("加密前数据" + str);
            str = AESUtils.encrypt(str);
            log.info("加密后数据" + str);
        } catch (Exception e) {
            throw new ApiException(SystemException.ExceptionHolder.ENCRYPT_ERROR);
        }
        return str;
    }
}
