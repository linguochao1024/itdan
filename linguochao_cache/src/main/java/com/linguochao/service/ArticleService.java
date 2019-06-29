package com.linguochao.service;

import com.linguochao.dao.ArticleDao;
import com.linguochao.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Article findById(String id) {
        //redisTemplate.boundValueOps("key").set("value");   // 先绑定某个key，然后进行赋值
		/*redisTemplate.opsForValue().set("key","value");   // 直接设置某个key和value


        BoundValueOperations bos = redisTemplate.boundValueOps("key");

        bos.set("value");
        bos.expire();
        bos.get();

        redisTemplate.opsForValue().set("key","value",20, TimeUnit.SECONDS);
        redisTemplate.opsForValue().get("key");*/


        //1.先到redis查询是否存在该文章
        Article article = (Article)redisTemplate.opsForValue().get("article_"+id);

        if(article==null){
            //1.1 如果没有，则从mysql数据库查询，把该文章放入redis
            article = articleDao.findById(id).get();

            redisTemplate.opsForValue().set("article_"+id,article);

            //设置过期时间（30s）
            //redisTemplate.opsForValue().set("article_"+id,article,30,TimeUnit.SECONDS);
        }

        //1.2 如果有，直接返回
        return article;
    }

    /**
     * 增加
     * @param article
     */
    public void add(Article article) {
        article.setId( idWorker.nextId()+"" );
        articleDao.save(article);
    }

    /**
     * 修改
     * @param article
     */
    public void update(Article article) {
        articleDao.save(article);
        //删除缓存
        redisTemplate.delete("article_"+article.getId());
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        articleDao.deleteById(id);
        //删除缓存
        redisTemplate.delete("article_"+id);
    }

    /**
     * 文章审核
     */
    @Transactional
    public void examine(String id){
        articleDao.examine(id);
    }

    /**
     * 文章点赞
     */
    @Transactional
    public void thumbup(String id){
        articleDao.thumbup(id);
    }
}
