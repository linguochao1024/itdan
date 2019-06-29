package com.linguochao.controller;

import com.linguochao.pojo.Label;
import com.linguochao.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 标签Controller
 * 基础CRUD 分页 条件
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController   // @RestController = @Controller + @ResponseBody
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;


    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功6666",labelService.findAll());
    }

    /**
     * 查询一个
     *     @RequestParam: 接收查询参数   例如   /label?id=10
     *     @PathVariable:  接收路径参数  例如  /label/10
     *
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        System.out.println("调用No3...");
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(id));
    }

    /**
     * 添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){  // @RequestBody: 将请求正文json字符串转换为Java对象
        labelService.add(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Label label){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 条件查询
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        List<Label> list = labelService.findSearch(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 带条件的分页查询
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSeaerch(@RequestBody Map searchMap,@PathVariable int page,@PathVariable int size){
        Page<Label> pageData = labelService.findSearch(searchMap,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }
}
