package com.linguochao.post;

import com.linguochao.exception.ApiException;
import com.linguochao.exception.SystemException;
import com.linguochao.utils.AESUtils;
import com.linguochao.utils.Constants;
import com.linguochao.utils.Md5Utils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2019/8/28 17:07
 */
public class ApiHttpInputMessage implements HttpInputMessage {
    private static final Logger logger = LoggerFactory.getLogger(ApiHttpInputMessage.class);

    private HttpHeaders httpHeaders;

    private InputStream body;

    public ApiHttpInputMessage(HttpInputMessage inputMessage) throws IOException {
        String inputStreamBody = IOUtils.toString(inputMessage.getBody(), Constants.CHARSET);
        String decryptBody = null;
        try {
            logger.info("前端数据："+ inputStreamBody);
            decryptBody = AESUtils.desEncrypt(inputStreamBody);
            logger.info("解密后数据："+ decryptBody);
        } catch (Exception e) {
            throw new ApiException(SystemException.ExceptionHolder.DECRYPT_ERROR);
        }

        // 签名
        if (Constants.MUST_CHECK_SIGN) {
            String sign = inputMessage.getHeaders().getFirst(Constants.SING_KEY);
            String nonce = inputMessage.getHeaders().getFirst(Constants.NONCE_KEY);
            verifySign(sign, nonce, decryptBody, AESUtils.KEY);
        }

        this.httpHeaders = inputMessage.getHeaders();
        if (decryptBody != null) {
            this.body = IOUtils.toInputStream(decryptBody, Constants.CHARSET);
        }
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return httpHeaders;
    }


    /**
     * 签名认证
     * @param sign
     * @param nonce
     * @param body
     * @param secretKey
     */
    public static void verifySign(String sign, String nonce, String body, String secretKey){
        logger.info("========签名验证开始========");
        if (StringUtils.isEmpty(sign)) {
            throw new ApiException(SystemException.ExceptionHolder.SIGN_MISS);
        }
        if (StringUtils.isEmpty(nonce)) {
            throw new ApiException(SystemException.ExceptionHolder.NONCE_MISS);
        }
        String signPlain = body + nonce + secretKey;
        String localSign = Md5Utils.md5(signPlain).toUpperCase();
        if (!sign.equals(localSign)) {
            throw new ApiException(SystemException.ExceptionHolder.SIGN_WRONG);
        }
        logger.info("========签名验证通过========");
    }
}

