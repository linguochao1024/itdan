package com.linguochao.dao;

import com.linguochao.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin> {

    /**
     * 登录
     */
    public Admin findByLoginname(String loginname);
}
