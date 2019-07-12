package com.linguochao.feign.service;

import com.linguochao.feign.dao.SpitDao;
import com.linguochao.feign.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    //MongoTemplate： 提供一些方法，实现命令发送
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 查询所有
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 查询一个
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 修改
     */
    public void update(Spit spit) {  // spit: 必须存在数据库有的ID值
        spitDao.save(spit);
    }

    /**
     * 删除
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    /**
     * 根据上级ID查询吐槽
     */
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        /**
         * 命名查询+分页
         */
        return spitDao.findByParentid(parentid, PageRequest.of(page - 1, size));
    }

    /**
     * 添加
     */
    public void add(Spit spit) {
        //设置id
        spit.setId(idWorker.nextId() + "");
        spitDao.save(spit);

        //判断该信息是否为评论，如果是评论，该评论对应的吐槽的回复数+1
        if (spit.getParentid() != null && !spit.getParentid().equals("")) {

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            Update update = new Update();
            update.inc("comment", 1);

            mongoTemplate.updateFirst(query, update, "spit");
        }
    }

    /**
     * 第一种方案：吐槽点赞（效率不高）
     */
   /* public void thumbup(String id){
        //1.先查询当前点赞数
        Spit spit = findById(id);
        //2.对点赞数+1
        spit.setThumbup(spit.getThumbup()+1);
        //3.把新的点赞写回数据库
        update(spit);
    }*/

    /**
     * 第二种方案：吐槽点赞（推荐使用）
     */
    public void thumbup(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("thumbup", 1);

        mongoTemplate.updateFirst(query, update, "spit");
    }

    /**
     * 取消点赞
     *
     * @param id
     */
    public void cancelThumbup(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("thumbup", -1);

        mongoTemplate.updateFirst(query, update, "spit");
    }

}
