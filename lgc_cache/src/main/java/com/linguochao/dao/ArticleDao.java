package com.linguochao.dao;

import com.linguochao.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article> {

    /**
     * 文章审核
     */
    @Modifying
    @Query("update Article a set a.state = '1' where a.id = ?1")
    public void examine(String id);

    /**
     * 文章点赞
     */
    @Modifying
    @Query("update Article a set a.thumbup = a.thumbup + 1 where a.id = ?1")
    public void thumbup(String id);
}
