# SpringCache 的使用

Spring Cache 使用方法与 Spring 对事务管理的配置相似。Spring Cache 的核心就是对某个方法进行缓存，其实质就是缓存该方法的返回结果，并把方法参数和结果用键值对的方式存放到缓存中，当再次调用该方法使用相应的参数时，就会直接从缓存里面取出指定的结果进行返回。
常用注解：

```
@Cacheable-------使用这个注解的方法在执行后会缓存其返回结果。
@CacheEvict--------使用这个注解的方法在其执行前或执行后移除SpringCache中的某些元素。
```

## 1. 缓存实现
##### （1）为 GatheringApplication 添加@EnableCaching 开启缓存支持

```java
@SpringBootApplication
@EnableCaching
public class GatheringApplication {
}
```
##### （2）在 GatheringService 的 findById 方法添加缓存注解，这样当此方法第一次运行，在缓存中没有找到对应的 value 和 key，则将查询结果放入缓存。

```java
public class GatheringService {
/**
* 根据 ID 查询实体
* @param id
* @return
*/
@Cacheable(value="gathering", key="#id")
public Gathering findById(String id) {
	return gatheringDao.findById(id).get();
}
}
```
##### （3）当我们对数据进行删改的时候，需要更新缓存。其实更新缓存也就是清除缓存，因为清除缓存后，用户再次调用查询方法无法提取缓存会重新查找数据库中的记录并放入缓存。

```java
//在 GatheringService 的 update、deleteById 方法上添加清除缓存的注解
public class GatheringService {
/**
* 修改
*/
@CacheEvict(value="gathering",key="#gathering.id")
public void update(Gathering gathering) {
	gatheringDao.save(gathering);
}
/**
* 删除
*/
@CacheEvict(value="gathering",key="#id")
public void deleteById(String id) {
	gatheringDao.deleteById(id);
}
}
```
## 2.和redis的对比
 #### 1）代码实现角度
  SpringCache只需要在方法上面加上@Cacable @CacacheEvict就可以实现缓存，比较简单！
  Spring Data Redis需要修改原来的方法，加入逻辑才可以实现，相对复杂点！

 #### 2） 存储方式角度
  Spring Cache缓存在应用内存中，应用停止，缓存丢失啦！
  Spring Data Redis缓存在Redis服务器，Redis可以持久化，缓存不会轻易丢失！

 #### 3）分布式角度
   Spirng Cache只支持单机应用缓存，不支持分布式
   SpringData Redis可以在分布式环境下使用

 #### 4）过期时间设置
   SpringCache不支持过期时间设置
   SpringDataRedis支持设置
​    
结论：
如果你的项目的缓存数据，不需要在分布环境下使用，且不需要持久化，可以使用SpringCache
如果你的项目的缓存数据，需要分布式，或者需要持久态，都需要使用SpringDataRedis