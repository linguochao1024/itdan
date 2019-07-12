package com.linguochao.feign.dao;

import com.linguochao.feign.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
public interface FriendDao extends JpaRepository<Friend,String> {


    /**
     * 查询好友表的记录数
     */
    @Query("select count(f) from Friend f where f.userid = ?1 and f.friendid = ?2")
    public Long selectCount(String userid,String friendid);

    /**
     * 更新islike值
     */
    @Modifying
    @Query("update Friend f set f.islike = ?3 where f.userid = ?1 and f.friendid = ?2")
    public void updateLike(String userid,String friendid,String islike);

    /**
     * 删除好友记录
     *
     */
    @Modifying
    @Query("delete from Friend f where f.userid = ?1 and f.friendid = ?2")
    public void deleteFriendid(String userid,String friendid);
}
