package com.linguochao.feign.dao;

import com.linguochao.feign.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

    /**
     * 登录
     */
    public User findByMobile(String mobile);
}
