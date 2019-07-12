package com.linguochao.feign.service;

import com.linguochao.feign.dao.AdminDao;
import com.linguochao.feign.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private BCryptPasswordEncoder encoder;


    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        adminDao.deleteById(id);
    }

    /**
     * 登录
     */
    public Admin login(Admin admin){
        //1.判断账户是否存在
        Admin loginAdmin = adminDao.findByLoginname(admin.getLoginname());

        //2.如果账户存在，继续判断密码是否一致
        /**
         *
         *  判断密码是否一致的思路
         *     1）得到数据库密码对应的盐。（从数据库的密码提取出来）
         *     2）把  用户输入的密码进行哈希加密 + 第一步获取的盐 =  密码（包含盐）
         *     3）第二步生成密码 和 数据库的密码进行匹配
         *
         */
        if(loginAdmin!=null &&  encoder.matches(admin.getPassword(),loginAdmin.getPassword())  ){
            //登录成功
            return loginAdmin;
        }else{
            //登录失败
            return null;
        }
    }
}
