package com.linguochao.dao;

import com.linguochao.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit> {

    /**
     * 推荐职位
     */
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /**
     * 最新职位
     */
    public List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}

