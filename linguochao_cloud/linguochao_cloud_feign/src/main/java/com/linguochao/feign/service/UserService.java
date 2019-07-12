package com.linguochao.feign.service;

import com.linguochao.feign.dao.UserDao;
import com.linguochao.feign.pojo.User;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * 获取手机验证码
     */
    public void sendsms(String mobile){
        //1）获取用户输入的手机号码
        //2）在系统生成6位随机数字验证码   apache commons-lang.jar   RandomStringUtils 随机字符串
        String code = RandomStringUtils.randomNumeric(6);

        //3）把验证码存入redis
        redisTemplate.opsForValue().set("sendsms_"+mobile,code,3, TimeUnit.MINUTES);

        //4）把手机号码和验证码一起发送RabbitMQ
        Map msg = new HashMap();
        msg.put("mobile",mobile);
        msg.put("code",code);
        rabbitMessagingTemplate.convertAndSend("sms",msg);
    }
    /**
     * 用户注册
     */
    public Boolean register(String code,User user){
        //1.从redis取出系统发送的验证码
        String redisCode = (String)redisTemplate.opsForValue().get("sendsms_"+user.getMobile());

        //2.判断用户输入的验证和系统生成的是否一致
        if(redisCode!=null && redisCode.equals(code)){
            //保存用户信息
            user.setId(idWorker.nextId()+"");
            //初始化信息
            user.setRegdate(new Date());//注册时间
            user.setFanscount(0);//粉丝数
            user.setFollowcount(0);//关注数
            user.setPassword(encoder.encode(user.getPassword()));
            userDao.save(user);
            return true;
        }
        return false;
    }

    /**
     * 登录
     */
    public User login(User user){
        //1.判断账户是否存在
        User loginUser = userDao.findByMobile(user.getMobile());
        //2.账户存在，判断密码是否一致
        if( loginUser!=null && encoder.matches(user.getPassword(),loginUser.getPassword())  ){
            //登录成功
            return loginUser;
        }else{
            //登录失败
            return null;
        }
    }

}
