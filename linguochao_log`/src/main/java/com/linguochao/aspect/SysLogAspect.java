package com.linguochao.aspect;

import com.linguochao.annotation.SysLog;
import com.linguochao.entity.LoginLogEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import util.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author linguochao
 * @Description 系统日志，切面处理类
 * @Date 2019/7/21 16:01
 */
public class SysLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.linguochao.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SysLog syslog = method.getAnnotation(SysLog.class);

        //获取注解的值,判断是什么日志
        switch (syslog.type()) {
            case LOGIN:  //登录
                saveSysLoginLog(syslog, result);
                break;
            case EXIT:  //退出登录
                saveSysLogoutLog(syslog);
                break;
        }
        if(result instanceof Exception){
            //可以自定义异常处理
            throw (Exception)result;
        }

        return result;
    }

    private void saveSysLogoutLog(SysLog syslog) {
    }

    private void saveSysLoginLog(SysLog syslog, Object result) {
        try{
            LoginLogEntity loginLogEntity;
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String ip = IpUtils.getIpAdrress(request);
            String userId = (String) request.getAttribute("USER_ID");   //取出登录用户ID
            String userName = (String) request.getAttribute("USER_NAME");   //取出登录用户

            if(result != null && result instanceof Exception){    //出现异常
                Exception e = (Exception)result;
                String message = e.getMessage();
                //日志实体类,封装异常信息
                loginLogEntity=new LoginLogEntity();

            }else{
                //日志实体类,封装正常登陆信息
                loginLogEntity=new LoginLogEntity();

            }
            //保存系统日志
            //loginLogDao.save(loginLogEntity);

        }catch(Exception e){
            logger.error("创建登录日志失败:", e);
        }
    }



}
