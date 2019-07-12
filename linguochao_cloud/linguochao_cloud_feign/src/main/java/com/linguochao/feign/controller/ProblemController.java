package com.linguochao.feign.controller;

import com.linguochao.feign.pojo.Problem;
import com.linguochao.feign.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问题Controller
 * SQL查询
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    /**
     * 最新问答
     */
    @RequestMapping(value = "/newlist/{labelid}/{page}/{size}" ,method = RequestMethod.GET)
    public Result newlist(@PathVariable String labelid, @PathVariable int page, @PathVariable int size){
        Page<Problem> pageData = problemService.newlist(labelid,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    /**
     * 热门问答
     */
    @RequestMapping(value = "/hotlist/{labelid}/{page}/{size}" ,method = RequestMethod.GET)
    public Result hotlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
        Page<Problem> pageData = problemService.hotlist(labelid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    /**
     * 等待问答
     */
    @RequestMapping(value = "/waitlist/{labelid}/{page}/{size}" ,method = RequestMethod.GET)
    public Result waitlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
        Page<Problem> pageData = problemService.waitlist(labelid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }
}
