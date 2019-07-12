package com.linguochao.jpa.dao;

import com.linguochao.jpa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem> {

    @Query(value = "SELECT * FROM tb_problem p WHERE p.id IN " +
            "(SELECT pl.problemid FROM tb_pl pl WHERE pl.labelid = ?) " +
            "ORDER BY p.replytime DESC",nativeQuery = true)
    public Page<Problem> newlist(String labelid, Pageable pageable);

    /**
     * 热门问答
     */
    @Query(value = "SELECT * FROM tb_problem p WHERE p.id IN " +
            "(SELECT pl.problemid FROM tb_pl pl WHERE pl.labelid = ?) " +
            "ORDER BY p.reply DESC",nativeQuery = true)
    public Page<Problem> hotlist(String labelid, Pageable pageable);

    /**
     * 等待问答
     */
    @Query(value = "SELECT * FROM tb_problem p WHERE p.id IN " +
            "(SELECT pl.problemid FROM tb_pl pl WHERE pl.labelid = ?) " +
            " AND p.reply = 0 ORDER BY p.createtime DESC",nativeQuery = true)
    public Page<Problem> waitlist(String labelid, Pageable pageable);

}
