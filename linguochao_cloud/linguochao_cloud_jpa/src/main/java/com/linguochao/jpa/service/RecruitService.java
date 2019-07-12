package com.linguochao.jpa.service;

import com.linguochao.jpa.dao.RecruitDao;
import com.linguochao.jpa.pojo.Recruit;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RecruitService {

    @Autowired
    private RecruitDao recruitDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 推荐职位
     */
    public List<Recruit> recommend() {
        /**
         *  state为2   findByState("2")   where state = 2
         按照createtime倒序   OrderByCreatetimeDesc    order by createtie desc
         取前面4条     Top4       limit 0,4
         */
        return recruitDao.findTop4ByStateOrderByCreatetimeDesc("2");
    }

    /**
     * 最新职位
     */
    public List<Recruit> newlist() {
        /**
         *  state不为0   findByStateNot("0")   where state <> 0
         按照createtime倒序   OrderByCreatetimeDesc    order by createtie desc
         取前面12条     Top12       limit 0,12
         */
        return recruitDao.findTop12ByStateNotOrderByCreatetimeDesc("0");
    }
}

