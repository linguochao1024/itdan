package com.linguochao.feign.controller;

import com.linguochao.feign.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    /**
     *  添加好友或非好友
     */
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type){
        //1.获取当前登录用户ID（userid）
        Claims claims = (Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESS_ERROR,"请先登录");
        }
        String userid = claims.getId();

        //2.判断type是1还是2
        if("1".equals(type)){
            //添加好友
            Integer flag = friendService.addFriend(userid,friendid);

            //判断是否添加成功
            if(flag==1){
                return new Result(true,StatusCode.OK,"添加好友成功");
            }else{
                return new Result(false,StatusCode.REPEATE_ERROR,"请不要重复添加好友");
            }
        }else{
            //添加非好友
            friendService.addNoFriend(userid,friendid);

            return new Result(true,StatusCode.OK,"添加非好友成功");
        }

    }

    /**
     * 删除好友
     */
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        //1.获取登录用户ID
        Claims claims = (Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESS_ERROR,"请先登录");
        }
        String userid = claims.getId();

        friendService.deleteFriend(userid,friendid);

        return new Result(true,StatusCode.OK,"删除好友成功");
    }

}

