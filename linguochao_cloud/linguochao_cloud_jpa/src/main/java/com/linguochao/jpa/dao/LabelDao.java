package com.linguochao.jpa.dao;

import com.linguochao.jpa.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 标签dao接口
 * JpaRepository: 拥有CRUD方法
 */
public interface LabelDao extends JpaRepository<Label,String>,JpaSpecificationExecutor<Label> {
}
