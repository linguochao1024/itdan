package com.linguochao.dao;

import com.linguochao.pojo.User;
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
