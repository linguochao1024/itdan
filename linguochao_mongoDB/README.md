#  一. Docker  安装 MongoDB
在宿主机创建 mongo 容器
```shell
docker run -di --name=linguochao_mongo -p 27017:27017 mongo
```
远程登陆
```shell
mongo 192.168.184.134
```
#  二. SpringDataMongoDB
SpringData 家族成员之一，用于操作 MongoDb 的持久层框架，封装了底层的mongodbdriver。
官网主页： https://projects.spring.io/spring-data-mongodb/
我们十次方项目的吐槽微服务就采用 SpringDataMongoDB 框架

###  1. 需求分析
采用 SpringDataMongoDB 框架实现吐槽微服务的持久层。
实现功能：
（1）基本增删改查 API
（2）根据上级 ID 查询吐槽列表
（3）吐槽点赞
（4）发布吐槽

###  2. 模块搭建
#####  1）搭建子模块 
#####  2）pom.xml 依赖导入
```pom
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
<dependency>
    <groupId>com.linguochao</groupId>
    <artifactId>linguochao_common</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
#####  3）创建 application.yml
```yml
server:
    port: 9006
spring:
    application:
        name: linguochao‐spit #指定服务名
    data:
        mongodb:	
            host: 192.168.184.134
          	  database: spitdb
```
#####  4）创建启动类
```java
//  吐槽微服务
@SpringBootApplication
public class SpitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpitApplication.class,args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
```


 ###  3.基本增删改查 API  实现
 #####  实体类
```java
//吐槽
@Document(collection = "spit")
public class Spit implements Serializable{
@Id
private String id;

@Field("content")
private String content;
private Date publishtime;
private String userid;
private String nickname;
private Integer visits;
private Integer thumbup;
private Integer share;
private Integer comment;
private String state;
private String parentid; // 父 id
}
```
#####  Controller
```java
/**
* 吐槽 Controller
*/
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
@Autowired
private SpitService spitService;

/* 查询全部数据*/
@RequestMapping(method= RequestMethod.GET)
public Result findAll(){
return new Result(true, StatusCode.OK," 查询成功",spitService.findAll());
}

/* 根据 ID 查询*/
@RequestMapping(value="/{id}",method= RequestMethod.GET)
public Result findById(@PathVariable String id){
return new Result(true,StatusCode.OK," 查询成功",spitService.findById(id));
}

/*增加*/
@RequestMapping(method=RequestMethod.POST)
public Result add(@RequestBody Spit spit ){
spitService.add(spit);
return new Result(true,StatusCode.OK," 增加成功");
}

/* 修改*/
@RequestMapping(value="/{id}",method= RequestMethod.PUT)
public Result update(@RequestBody Spit spit, @PathVariable String id ){
spit.setId(id);
spitService.update(spit);
return new Result(true,StatusCode.OK," 修改成功");
}

/* 删除*/
@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
public Result delete(@PathVariable String id ){
spitService.deleteById(id);
return new Result(true,StatusCode.OK," 删除成功");
}
}
```
#####  Service
```java
/**
* 吐槽 service
*/
@Service
public class SpitService {

@Autowired
private SpitDao spitDao;
@Autowired
private IdWorker idWorker;

/* 查询所有*/
public List<Spit> findAll(){
	return spitDao.findAll();
}

/* 根据 id 查询*/
public Spit findById(String id){
	return spitDao.findById(id).get();
}

/* 添加*/
public void add(Spit spit){
spit.setId(idWorker.nextId()+"");
	spitDao.save(spit);
}

/* 修改*/
public void update(Spit spit){
	spitDao.save(spit);
}

/* 删除*/
public void deleteById(String id){
	spitDao.deleteById(id);
}
}
```
 #####  Dao
```java
public interface SpitDao extends MongoRepository<Spit,String>{
}
```


