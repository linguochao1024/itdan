package com.linguochao.feign.controller;

import com.linguochao.feign.pojo.Recruit;
import com.linguochao.feign.service.RecruitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 职位Controller
 * 命名方式查询
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 推荐职位
     */
    @RequestMapping(value = "/search/recommend" ,method = RequestMethod.GET)
    public Result recommend(){
        List<Recruit> list = recruitService.recommend();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    /**
     * 最新职位
     */
    @RequestMapping(value = "/search/newlist" ,method = RequestMethod.GET)
    public Result newlist(){
        List<Recruit> list = recruitService.newlist();
        return new Result(true,StatusCode.OK,"查询成功",list);
    }
}
