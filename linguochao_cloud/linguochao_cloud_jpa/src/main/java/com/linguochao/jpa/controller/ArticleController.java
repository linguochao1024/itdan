package com.linguochao.feign.controller;

import com.linguochao.feign.pojo.Article;
import com.linguochao.feign.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 文章Controller
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 搜索方法
     */
    @RequestMapping(value = "/search/{keyword}/{page}/{size}" ,method = RequestMethod.GET)
    public Result search(@PathVariable String keyword, @PathVariable int page, @PathVariable int size){
        Page<Article> pageData = articleService.search(keyword,page,size);
        return new Result(true, StatusCode.OK,"搜索成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

}

