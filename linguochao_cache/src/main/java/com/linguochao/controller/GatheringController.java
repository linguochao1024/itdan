package com.linguochao.controller;

import com.linguochao.pojo.Gathering;
import com.linguochao.service.GatheringService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 缓存-SpringCache
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;

    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功",gatheringService.findById(id));
    }

    /**
     * 修改
     * @param gathering
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Gathering gathering, @PathVariable String id ){
        gathering.setId(id);
        gatheringService.update(gathering);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        gatheringService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
