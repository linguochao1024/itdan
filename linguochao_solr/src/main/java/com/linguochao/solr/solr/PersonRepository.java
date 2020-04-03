package com.linguochao.solr.solr;

import com.linguochao.solr.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.SolrCrudRepository;
import java.util.List;

/**
 * @author linguochao
 * @Description solr数据访问层
 * @Date 2020/3/24 14:22
 */
public interface PersonRepository extends SolrCrudRepository<Person, Long> {

    long countByLastname(String lastname);

    long deleteByLastname(String lastname);

    List<Person> removeByLastname(String lastname);

    List<Person> findByLastname(String lastname);

    List<Person> findByLastnameOrderByFirstnameAsc(String lastname);

    Page<Person> queryFirst10ByLastname(String lastname, Pageable pageable);

    List<Person> findFirst10ByLastname(String lastname, Sort sort);

    List<Person> findTop10ByLastname(String lastname, Pageable pageable);
}
