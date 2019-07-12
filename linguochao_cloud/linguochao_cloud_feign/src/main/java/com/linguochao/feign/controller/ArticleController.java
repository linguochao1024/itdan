package com.linguochao.feign.controller;

import com.linguochao.feign.pojo.Article;
import com.linguochao.feign.service.ArticleService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章Controller
 * 缓存-Redis
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功",articleService.findById(id));
    }

    /**
     * 增加
     * @param article
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Article article  ){
        articleService.add(article);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     * @param article
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id ){
        article.setId(id);
        articleService.update(article);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        articleService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 文章审核
     */
    @RequestMapping(value = "/examine/{id}" ,method = RequestMethod.PUT)
    public Result examine(@PathVariable String id){
        articleService.examine(id);
        return new Result(true,StatusCode.OK,"审核成功");
    }

    /**
     * 文章点赞
     */
    @RequestMapping(value = "/thumbup/{id}" ,method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String id){
        articleService.thumbup(id);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
