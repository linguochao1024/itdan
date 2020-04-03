package com.linguochao.solr.service;

import com.linguochao.solr.entity.Person;
import com.linguochao.solr.solr.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/3/24 14:28
 */
public class UserService {

    @Autowired
    private PersonRepository personRepository;

    public void listUser(){

        Person user=new Person();
        Person save = personRepository.save(user);
        Person user1 = personRepository.findById(1L).get();
        Iterable<Person> all1 = personRepository.findAll();
        long count = personRepository.count();
        personRepository.delete(user);

        Page<Person> all = personRepository.findAll(PageRequest.of(1, 20));

        Sort sort = Sort.by("firstname").ascending()
                .and(Sort.by("lastname").descending());
        Iterable<Person> all2 = personRepository.findAll(sort);


    }
}
