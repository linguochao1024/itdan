package com.linguochao.feign.dao;

import com.linguochao.feign.pojo.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GatheringDao extends JpaRepository<Gathering,String>,JpaSpecificationExecutor<Gathering> {

}
