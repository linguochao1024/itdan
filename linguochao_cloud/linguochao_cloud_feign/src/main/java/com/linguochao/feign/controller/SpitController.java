package com.linguochao.feign.controller;

import com.linguochao.feign.pojo.Spit;
import com.linguochao.feign.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 查询一个
     *     @RequestParam: 接收查询参数   例如   /spit?id=10
     *     @PathVariable:  接收路径参数  例如  /spit/10
     *
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     * 添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){  // @RequestBody: 将请求正文json字符串转换为Java对象
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Spit spit){
        spit.setId(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据上级ID查询吐槽
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageData = spitService.findByParentid(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 吐槽点赞
     */
    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String id){
        //模拟登录用户ID
        String userid = "1001";

        //1.从redis查询是否有该用户的点赞记录
        String flag = (String)redisTemplate.opsForValue().get("thumbup_"+userid+"_"+id);

        //1.1 如果存在，用户点赞过啦，提示用户不能点赞（或取消点赞）
        if("1".equals(flag)){

            // return new Result(false,StatusCode.REPEATE_ERROR,"不能重复点赞");

            //调用取消点赞
            spitService.cancelThumbup(id);

            //删除缓存
            redisTemplate.delete("thumbup_"+userid+"_"+id);
            return new Result(true, StatusCode.OK,"取消点赞成功");
        }
        //1.2 如果不存在，继续执行点赞逻辑，把点击记录存入redis中
        spitService.thumbup(id);

        redisTemplate.opsForValue().set("thumbup_"+userid+"_"+id,"1");
        return new Result(true,StatusCode.OK,"点赞成功");
    }

}

