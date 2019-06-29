package com.linguochao.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信消费者
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.temp_code}")
    private String tempCode;

    @Value("${aliyun.sms.sign_name}")
    private String signName;

    /**
     * 消息处理方法
     */
    @RabbitHandler
    public void handlerMsg(Map msg){
        String mobile = (String)msg.get("mobile");
        String code = (String)msg.get("code");
        System.out.println("手机号码："+mobile);
        System.out.println("验证码："+code);

        //使用阿里短信发送手机验证码
        try {
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(mobile, tempCode, signName, "{\"code\":\"" + code + "\"}");

            if(sendSmsResponse.getCode().equals("OK")){
                System.out.println("短信发送成功");
            }else{
                System.out.println("短信发送失败："+sendSmsResponse.getCode());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
