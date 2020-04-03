package com.linguochao.solr.service;

import com.linguochao.solr.entity.Person;
import com.linguochao.solr.entity.SearchParams;
import com.linguochao.solr.solr.PersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.List;
import java.util.Optional;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/3/24 14:28
 */
public class UserService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    SolrTemplate solrTemplate;


    /**
     * 新增或者更新
     * @param person
     * @return
     */
    public Person saveOrUpdate(Person person){
        Person save = personRepository.save(person);
        return save;
    }

    /**
     * 批量新增或者更新
     * @param persons
     * @return
     */
    public void saveOrUpdateList(List<Person> persons){
        personRepository.saveAll(persons);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(Long id){
        personRepository.deleteById(id);
    }


    /**
     * 分页查找
     * @param pageNum
     * @param pageSize
     * @return
     */
    public  Page<Person> findByPage(Integer pageNum,Integer pageSize){
        Page<Person> page = personRepository.findAll(PageRequest.of(pageNum, pageSize));
        return page;
    }

    public void queryByCondition(SearchParams params){

        /** 创建查询对象 */
        Query query = new SimpleQuery("*:*");

        //关键字
        if (StringUtils.isNoneBlank(params.getKeywords())){
            Criteria criteria = new Criteria("keywords").startsWith(params.getKeywords());
            /** 添加查询条件 */
            query.addCriteria(criteria);
        }

        //部门路径
        if (params.getStatus().equals(0)){
            Criteria criteria = new Criteria("departId").is(params.getDepartId());
            query.addCriteria(criteria);
        }else {
            Criteria criteria = new Criteria("departPath").startsWith(params.getDepartmentPath());
            query.addCriteria(criteria);
        }

        //状态
        Criteria criteria = new Criteria("status").is(params.getStatus());
        query.addCriteria(criteria);


        //排序
        Sort sort = Sort.by("orderNumber").ascending();
        query.addSort(sort);

        //分页
        PageRequest page = PageRequest.of(1, 20);
        query.setPageRequest(page);

        ScoredPage<Person> people = solrTemplate.queryForPage(query, Person.class);
        List<Person> content = people.getContent();
        int size = people.getSize();
        int totalPages = people.getTotalPages();

    }


}
