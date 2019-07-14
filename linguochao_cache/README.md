# Redis 的使用

## 1.Redis  环境搭建
我们以 docker 的形式搭建 Redis 服务
```
docker run -di --name=linguochao_redis -p 6379:6379 redis
```

## 2.SpringDataRedis  简介
Spring-data-redis 是spring大家族的一部分，提供了在srping应用中通过简单的配置访问 redis
服务，对 reids 底层开发包(Jedis, JRedis, and RJC)进行了高度封装，RedisTemplate 提供了 redis
各种操作。

## 3.案例实现文章的缓存处理
#### 1.查询文档缓存
（1） pom.xml 引入依赖
```pom
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
（2）修改 application.yml ,在 spring 节点下添加配置
```ym
spring
	redis:
		host: 192.168.184.134
```
（3）修改 ArticleService 引入 RedisTemplate，并修改 findById 方法
```java
public class ArticleService{
// 根据 ID 查询实体
public Article findById(String id) {
    // 先从 redis 查询出文章
    Article article = (Article)redisTemplate.opsForValue().get("article_" + id );

    // 如果没有文章才从数据库查询
    if(article==null){
        article = articleDao.findById(id).get();
        redisTemplate.opsForValue().set("article_"+id,article);    // 放入 redis 缓存
    }	
	return article;
}
}
```
boundValueOps操作Redis
```java
public class ArticleService{ 
    Article article = (Article)redisTemplate.boundValueOps("article_" + id ).get();
    redisTemplate.boundValueOps("article_" + id ).set( article );
}
```

#### 2.  修改或删除后清除缓存
```java
//修改
public class ArticleService{
    public void update(Article article) {
        redisTemplate.delete( "article_" + article.getId() );	// 删除缓存
        articleDao.save(article);
    }
    // 删除
    public void deleteById(String id) {
        redisTemplate.delete( "article_" + id );	// 删除缓存
        articleDao.deleteById(id);
    }
}
```
#### 3.  缓存过期处理  --  修改 findById 方法 ，设置 1 天的过期时间：
```
// 放入 redis 缓存
redisTemplate.opsForValue().set("article_" + id, article,1, TimeUnit.DAYS);

//为了方便测试，我们可以把过期时间改为 10 秒，然后观察控制台输出
redisTemplate.opsForValue().set("article_" + id, article,10, TimeUnit.SECONDS);

```



