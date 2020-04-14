package com.linguochao.solr.service;

import com.linguochao.solr.entity.Person;
import com.linguochao.solr.dto.SearchParams;
import com.linguochao.solr.solr.PersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/3/24 14:28
 */
@Service
public class PersonService {

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

    /**
     * 根据条件查询SolrPerson
     * @param params
     * @return
     */
    public ScoredPage<Person> queryByCondition(SearchParams params){


        /** 创建查询对象 */
        Query query = new SimpleQuery("*:*");

        //关键字
        if (StringUtils.isNoneBlank(params.getKeywords())){
            Criteria criteria =(new Criteria("name").startsWith(params.getKeywords()));
            /** 添加查询条件 */
            query.addCriteria(criteria);
        }

        //部门路径
        if (params.getIsAll().equals(0)){
            //查当前部门下
            Criteria criteria = new Criteria("departid").is(params.getDepartId());
            query.addCriteria(criteria);
            //排序
            Sort sort = new Sort(Sort.Direction.DESC, "ordernumber_"+params.getDepartId());
            query.addSort(sort);
        }else {
            //查询当前部门及子部门
            Criteria criteria = new Criteria("departpath").startsWith(params.getDepartmentPath());
            query.addCriteria(criteria);
        }


        if (params.getStatus()!=null){
            //状态
            Criteria criteria = new Criteria("status").is(params.getStatus());
            query.addCriteria(criteria);
        }


        //分页
        PageRequest page = new  PageRequest(0, 20);
        query.setPageRequest(page);

        ScoredPage<Person> people = solrTemplate.queryForPage("collection1",query, Person.class);
        List<Person> content = people.getContent();
        validateData(content,params.getDepartId(),params.getIsAll());

        return people;
    }

    private List<Person> validateData(List<Person> content,Integer departId,Integer isAll){

        content.forEach(solrPerson -> {
            if (isAll==0){
                Long ordernumber = solrPerson.getOrdernumberMap().get(departId.toString());
                solrPerson.setOrdernumber(ordernumber);
            }
        });

        return content;
    }

    public static void main(String[] args) {
        String managecode="123";
        String s = String.valueOf(managecode.charAt(managecode.length()-1));
        System.out.println(s);
    }


}
