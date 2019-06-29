package com.linguochao.service;

import com.linguochao.dao.ProblemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 最新问答
     */
    public Page<Problem> newlist(String labelid, int page, int size){
        return problemDao.newlist(labelid, PageRequest.of(page-1,size));
    }

    /**
     * 热门问答
     */
    public Page<Problem> hotlist(String labelid, int page, int size){
        return problemDao.hotlist(labelid,PageRequest.of(page-1,size));
    }

    /**
     * 等待问答
     */
    public Page<Problem> waitlist(String labelid,int page,int size){
        return problemDao.waitlist(labelid,PageRequest.of(page-1,size));
    }
}
