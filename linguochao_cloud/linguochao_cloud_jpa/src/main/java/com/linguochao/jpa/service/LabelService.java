package com.linguochao.jpa.service;

import com.linguochao.jpa.dao.LabelDao;
import com.linguochao.jpa.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签service
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 查询一个
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 添加
     */
    public void add(Label label){
        //设置id
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改
     */
    public void update(Label label){  // label: 必须存在数据库有的ID值
        labelDao.save(label);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 条件查询
     */
    public List<Label> findSearch(Map searchMap){
        Specification<Label> spec = createSpecification(searchMap);
        return labelDao.findAll(spec);
    }

    /**
     * 带条件的分页
     */
    public Page<Label> findSearch(Map searchMap, int page, int size){
        Specification<Label> spec = createSpecification(searchMap);
        //Pageable的page从0开始的
        return labelDao.findAll(spec, PageRequest.of(page-1,size));
    }

    /**
     * 创建Specification对象
     */
    private Specification<Label> createSpecification(Map searchMap){
        //提供Specification接口的匿名内部类形式
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                //1.准备List集合，用于封装查询条件对象(Predicate)
                List<Predicate> preList = new ArrayList<Predicate>();

                //2.根据页面条件，封装查询条件对象，把查询对象放入List集合中
                if( searchMap.get("labelname")!=null && !searchMap.get("labelname").equals("")  ){
                    //   sql:  labelname like '%xxx%'
                    preList.add( cb.like(root.get("labelname").as(String.class),"%"+searchMap.get("labelname")+"%"));
                }
                if( searchMap.get("state")!=null && !searchMap.get("state").equals("")  ){
                    //   sql:  state = '0'
                    preList.add( cb.equal(root.get("state").as(String.class),searchMap.get("state")) );
                }
                if( searchMap.get("count")!=null){
                    //   sql:  count = 10
                    preList.add( cb.equal(root.get("count").as(Long.class),searchMap.get("count")) );
                }

                //3.把条件进行连接
                // where labelname like '%xxx%' and state = '0'
                Predicate[] preArray = new Predicate[preList.size()];
                //preList.toArray(preArray): 把preList集合的每个元素取出，放入preArray数组里面，最后返回preArray数组内容
                return cb.and(preList.toArray(preArray));
            }
        };
    }
}
