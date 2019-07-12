#  一. docker安裝 
（1）下载镜像：（此步省略）
```
docker pull rabbitmq:management
```

（2）创建容器，rabbitmq 需要有映射以下端口: 5671 5672 4369 15671 15672 25672

```
 15672 (if management plugin is enabled)
 15671 management 监听端口
 5672, 5671 (AMQP 0-9-1 without and with TLS)
 4369 (epmd) epmd 代表 Erlang 端口映射守护进程
 25672 (Erlang distribution)
```

```
docker run -di --name=linguochao_rabbitmq -p 5671:5617 -p 5672:5672 -p
4369:4369 -p 15671:15671 -p 15672:15672 -p 25672:25672 rabbitmq:management
```
浏览器访问 http://192.168.184.134:15672/#/

#  二.案例: 发送短信验证码--直接模式

实现思路： 在用户微服务编写 API ,生成手机验证码，存入 Redis 并发送到 RabbitMQ

###  1. 准备工作
（1）因为要用到缓存和消息队列，所以在用户微服务（linguochao_user）引入依赖 redis 和
amqp 的起步依赖。

```pom
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
（2）修改 application.yml ,在 spring 节点下添加配置
```ym
redis:
	host: 192.168.184.134
rabbitmq:
	host: 192.168.184.134
```

###  2. 代码实现
### 發送短信
#####  Controller
```java
public class UserController {
    /**
    * 发送短信验证码
    * @param mobile
    */
    @RequestMapping(value="/sendsms/{mobile}",method= RequestMethod.POST)
    public Result sendsms(@PathVariable String mobile ){
        userService.sendSms(mobile);
        return new Result(true,StatusCode.OK," 发送成功");
    }
}
```

#####  Service
（1）在 UserService 中新增方法，用于发送短信验证码

```java
public class UserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    /**
    * 发送短信验证码
    */
    public void sendSms(String mobile){
    // 生成 6 位随机验证码
    String code = RandomStringUtils.randomNumeric(6);
    System.out.println(" 生成的验证码为："+code);
    
    // 存入 redis （ 5 分钟有效）
    redisTemplate.opsForValue().set("smscode_"+mobile,code,5,TimeUnit.MINUTES);
    Map<String,String> map = new HashMap<String,String>();
    map.put("mobile",mobile);
    map.put("code",code);
    // 存入 mq 队列
    rabbitMessagingTemplate.convertAndSend("sms",map);
    }
}
```

### 用户注册
#####  Controller
在 UserController 添加方法

```java
public class UserController {
    /**
    * 用户注册
    * @param user
    */
    @RequestMapping(value="/register/{code}",method=RequestMethod.POST)
    public Result register( @RequestBody User user ,@PathVariable String code){
        userService.add(user,code);
        return new Result(true,StatusCode.OK," 注册成功");
    }
}
```

#####  Service
在 UserService 添加方法
```java
public class UserService {
/**
* 增加
* @param user 用户
* @param code 用户填写的验证码
*/
public void add(User user,String code) {
    // 判断验证码是否正确
    String syscode = (String)redisTemplate.opsForValue().get("smscode_"  + user.getMobile());
    
    // 提取系统正确的验证码
    if(syscode==null){
    throw new RuntimeException(" 请点击获取短信验证码");
    }
    if(!syscode.equals(code)){
    throw new RuntimeException(" 验证码输入不正确");
    }
    
    user.setId( idWorker.nextId()+"" );
    user.setFollowcount(0);				// 关注数
    user.setFanscount(0);				// 粉丝数
    user.setOnline(0L);					// 在线时长
    user.setRegdate(new Date());			// 注册日期
    user.setUpdatedate(new Date());		// 更新日期
    user.setLastdate(new Date());			// 最后登陆日期
    userDao.save(user);
    }
}
```
