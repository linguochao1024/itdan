package com.linguochao.feign.dao;
import com.linguochao.feign.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoRepository： 拥有CRUD功能
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */

public interface SpitDao extends MongoRepository<Spit,String> {

    /**
     * 根据上级ID查询吐槽
     */
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
