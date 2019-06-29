package com.linguochao.service;

import com.linguochao.client.UserClient;
import com.linguochao.dao.FriendDao;
import com.linguochao.dao.NoFriendDao;
import com.linguochao.pojo.Friend;
import com.linguochao.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userController;


    /**
     * 添加好友
     */
    @Transactional
    public Integer addFriend(String userid,String friendid){
        //1.判断是否已经添加好友
        if( friendDao.selectCount(userid,friendid)>0  ){
            //已经添加过好友，不需要添加
            return 0;
        }

        //没有添加过，继续添加
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");

        friendDao.save(friend);

        //判断对方有没有加过当前用户，如果有的话，把两条好友记录的islike同时改为1
        if(  friendDao.selectCount(friendid,userid)>0  ){
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }

        //增加关注数和粉丝数
        userController.updateFollowcount(userid,1);
        userController.updateFanscount(friendid,1);

        return 1;
    }

    /**
     * 添加非好友
     */
    public void addNoFriend(String userid,String friendid){
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);

        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     */
    @Transactional
    public void deleteFriend(String userid,String friendid){
        friendDao.deleteFriendid(userid,friendid);

        //判断对方有没有加过当前用户，有的话，把对方好友记录的islike改为0
        if(  friendDao.selectCount(friendid,userid)>0  ){
            friendDao.updateLike(friendid,userid,"0");
        }

        //减少关注数和粉丝数
        userController.updateFollowcount(userid,-1);
        userController.updateFanscount(friendid,-1);

    }
}

