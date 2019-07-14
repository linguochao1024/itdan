# 一.基于 JWT 的 Token 认证机制实现

### 1.什么是 JWT
JSON Web Token（JWT）是一个非常轻巧的规范。这个规范允许我们使用 JWT 在用户和服务器之间传递安全可靠的信息。

### 2.JWT  组成
一个 JWT 实际上就是一个字符串，它由三部分组成，头部、载荷与签名。

##### 头部（Header）
头部用于描述关于该 JWT 的最基本的信息，例如其类型以及签名所用的算法等。这也可以被表示成一个 JSON 对象。
```
{"typ":"JWT","alg":"HS256"}
```

在头部指明了签名算法是 HS256 算法。 我们进行 BASE64 编码 http://base64.xpcha.com/，
编码后的字符串如下：
```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
```
小知识：Base64 是一种基于 64 个可打印字符来表示二进制数据的表示方法。由于 2的 6 次方等于 64，所以每 6 个比特为一个单元，对应某个可打印字符。三个字节有 24个比特，对应于 4 个 Base64 单元，即 3 个字节需要用 4 个可打印字符来表示。JDK 中提供了非常方便的 BASE64Encoder 和 BASE64Decoder，用它们可以非常方便的完成基于 BASE64 的编码和解码

##### 载荷（playload）
载荷就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分
（1）标准中注册的声明（建议但不强制使用）
```
iss: jwt 签发者
sub: jwt 所面向的用户
aud: 接收 jwt 的一方
exp: jwt 的过期时间，这个过期时间必须要大于签发时间
nbf: 定义在什么时间之前，该 jwt 都是不可用的.
iat: jwt 的签发时间
jti: jwt 的唯一身份标识，主要用来作为一次性 token,从而回避重放攻击。
```

（2）公共的声明
公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息.
但不建议添加敏感信息，因为该部分在客户端可解密.

（3）私有的声明
私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，因为 base64是对称解密的，意味着该部分信息可以归类为明文信息。
这个指的就是自定义的 claim。比如前面那个结构举例中的 admin 和 name 都属于自定的claim。这些 claim 跟 JWT 标准规定的 claim 区别在于：JWT 规定的 claim，JWT 的接收方在拿到 JWT 之后，都知道怎么对这些标准的 claim 进行验证(还不知道是否能够验证)；而 private claims 不会验证，除非明确告诉接收方要对这些 claim 进行验证以及规则才行。
定义一个 payload:
```
{"sub":"1234567890","name":"John Doe","admin":true}
```
然后将其进行 base64 编码，得到 Jwt 的第二部分。
```
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9
```


##### 签证（signature）
jwt 的第三部分是一个签证信息，这个签证信息由三部分组成：
```
header (base64 后的)
payload (base64 后的)
secret
```

这个部分需要 base64加密后的 header 和base64加密后的 payload 使用.连接组成的字符串，然后通过 header 中声明的加密方式进行加盐 secret 组合加密，然后就构成了 jwt 的第三部分。
```
TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
```

将这三部分用.连接成一个完整的字符串,构成了最终的 jwt:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZS
I6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFO
NFh7HgQ
```
注意：secret 是保存在服务器端的，jwt 的签发生成也是在服务器端的，secret 就是用
来进行 jwt 的签发和 jwt 的验证，所以，它就是你服务端的私钥，在任何场景都不应该流
露出去。一旦客户端得知这个 secret, 那就意味着客户端是可以自我签发 jwt 了。

# 二.Java 的 的 JJWT  实现 JWT
### 1.什么是 JJWT
JJWT 是一个提供端到端的 JWT 创建和验证的 Java 库。永远免费和开源(Apache License，版本 2.0)，JJWT 很容易使用和理解。它被设计成一个以建筑为中心的流畅界面，隐藏了它的大部分复杂性。

### 2. Token  的创建
##### （1）创建 maven 工程，引入依赖
```
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.6.0</version>
</dependency>
```

#####（2）创建类 CreateJwtTest，用于生成 token
```
public class CreateJwtTest {
public static void main(String[] args) {
    JwtBuilder builder= Jwts.builder().setId("888")        // "jti"
            .setSubject(" 小白")						// "sub"
            .setIssuedAt(new Date())					// "ita"
            .signWith(SignatureAlgorithm.HS256, "itdan" );

    System.out.println( builder.compact() );
}
}
```
setIssuedAt 用于设置签发时间
signWith 用于设置签名秘钥

#####（3）测试运行，输出如下:
```
eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1Mj
M0MTM0NTh9.gq0J‐cOM_qCNqU_s‐d_IrRytaNenesPmqAIhQpYXHZk
```
再次运行，会发现每次运行的结果是不一样的，因为我们的载荷中包含了时间。


### 3. Token  的解析
我们刚才已经创建了 token ，在 web 应用中这个操作是由服务端进行然后发给客户端，客户端在下次向服务端发送请求时需要携带这个 token（这就好像是拿着一张门票一样），那服务端接到这个 token 应该解析出 token 中的信息（例如用户 id）,根据这些信息查询数据库返回相应的结果。

创建 ParseJwtTest：
```
public class ParseJwtTest {
public static void main(String[] args) {

    String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1
     MjM0MTM0NTh9.gq0J ‐cOM_qCNqU_s ‐d_IrRytaNenesPmqAIhQpYXHZk";

    Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();

    System.out.println("id:"+claims.getId());
    System.out.println("subject:"+claims.getSubject());
    System.out.println("IssuedAt:"+claims.getIssuedAt());
    }
}
```
试着将 token 或签名秘钥篡改一下，会发现运行时就会报错，所以解析 token 也就是验证token


###  4.Token  过期校验
有很多时候，我们并不希望签发的 token 是永久生效的，所以我们可以为 token 添加一个过期时间。
创建 CreateJwtTest2：
```
public class CreateJwtTest2 {
public static void main(String[] args) {

    // 为了方便测试，我们将过期时间设置为 1 分钟
    long now = System.currentTimeMillis();			// 当前时间
    long exp = now + 1000*60;					// 过期时间为 1 分钟
    JwtBuilder builder= Jwts.builder().setId("888")
            .setSubject(" 小白")
            .setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256,"itdan")
            .setExpiration(new Date(exp));
    System.out.println( builder.compact() );
    }
}
```
setExpiration 方法用于设置过期时间

修改 ParseJwtTest：
```
public class ParseJwtTest {
public static void main(String[] args) {

    String compactJws="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYX
     QiOjE1MjM0MTY1NjksImV4cCI6MTUyMzQxNjYyOX0.Tk91b6mvyjpKcl
     dkic8DgXz0zsPFFnRgTgkgcAsa9cc";

    Claims claims =Jwts.parser().setSigningKey("itdan").parseClaimsJws(compactJws).getBody();

    System.out.println("id:"+claims.getId());
    System.out.println("subject:"+claims.getSubject());
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    System.out.println(" 签发时间:"+sdf.format(claims.getIssuedAt()));
    System.out.println("过期时间:"+sdf.format(claims.getExpiration()));
    System.out.println(" 当前时间:"+sdf.format(new Date()) );
    }
}
```

测试运行，当未过期时可以正常读取，当过期时会引发
io.jsonwebtoken.ExpiredJwtException 异常。
```
Exception in thread "main" io.jsonwebtoken.ExpiredJwtException: JWT
expired at 2018‐06‐08T21:44:55+0800. Current time: 2018‐06‐
08T21:44:56+0800
at
io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:365)
at
io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:458)
at
io.jsonwebtoken.impl.DefaultJwtParser.parseClaimsJws(DefaultJwtParser.java
:518)
at cn.itcast.demo.ParseJwtTest.main(ParseJwtTest.java:13)
```


###  5.自动 claims
我们刚才的例子只是存储了 id 和 subject 两个信息，如果你想存储更多的信息（例如角色）可以定义自定义 claims 创建 CreateJwtTest3：
```
public class CreateJwtTest3 {
public static void main(String[] args) {
    
    long now = System.currentTimeMillis();		// 当前时间
    long exp = now + 1000*60;				// 过期时间为 1 分钟
    JwtBuilder builder= Jwts.builder().setId("888")
                    .setSubject(" 小白")
                    .setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256,"itcast")
                    .setExpiration(new Date(exp))
                    .claim("roles","admin")
                    .claim("logo","logo.png");
    System.out.println( builder.compact() );
    }
}
```

修改 ParseJwtTest：
```
public static void main(String[] args) {

    String   compactJws="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQ
    iOjE1MjM0MTczMjMsImV4cCI6MTUyMzQxNzM4Mywicm9sZXMiOiJhZG1pbiIsImx
    vZ28iOiJsb2dvLnBuZyJ9.b11p4g4rE94rqFhcfzdJTPCORikqP_1zJ1MP8KihYTQ";
    
         Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(compactJws).getBody();
    
    System.out.println("id:"+claims.getId());
    System.out.println("subject:"+claims.getSubject());
    System.out.println("roles:"+claims.get("roles"));
    System.out.println("logo:"+claims.get("logo"));
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    System.out.println(" 签发时间:"+sdf.format(claims.getIssuedAt()));
    System.out.println("时 过期时  间:"+sdf.format(claims.getExpiration()));
    System.out.println(" 当前时间:"+sdf.format(new Date()) );
    }
}
```

#  三.使用拦截器方式实现 Token  鉴权

如果我们每个方法都去写一段代码，冗余度太高，不利于维护，那如何做使我们的代码看起来更清爽呢？我们可以将这段代码放入拦截器去实现。
Spring 为我们提供了org.springframework.web.servlet.handler.HandlerInterceptorAdapter 这个适配器，继承此类，可以非常方便的实现自己的拦截器。
他有三个方法：分别实现预处理、后处理（调用了 Service 并返回 ModelAndView，但未进行页面渲染）、返回处理（已经渲染了页面）在preHandle 中，可以进行编码、安全控制等处理；
在 postHandle 中，有机会修改 ModelAndView；在 afterCompletion 中，可以根据 ex 是否为 null 判断是否发生了异常，进行日志记录。

##  删除用户功能鉴权
#### 1.创建拦截器类。
```
@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) throws Exception {
        
        System.out.println(" 经过了拦截器");
        
        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7); 		// The part after "Bearer"
            Claims claims = jwtUtil.parseJWT(token);
            if (claims != null) {
                if("admin".equals(claims.get("roles"))){			// 如果是管理员
                     request.setAttribute("admin_claims", claims);
                }
                if("user".equals(claims.get("roles"))){			// 如果是用户
                     request.setAttribute("user_claims", claims);
                }
            }
        }
        return true;
    }
}
```

#### 2.配置拦截器类,创建 com.linguochao.user.ApplicationConfig
```
@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtFilter jwtFilter;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtFilter).
        addPathPatterns("/**").
        excludePathPatterns("/**/login");    //注册界面放行
    }
}
```

#### 3.修改 UserController 的 delete 方法
```
/**
* 删除
* @param id
*/
@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
public Result delete(@PathVariable String id ){

    Claims claims=(Claims) request.getAttribute("admin_claims");
    if(claims==null){
     return new Result(true,StatusCode.ACCESSRROR," 无权访问");
    }
    userService.deleteById(id);
    return new Result(true,StatusCode.OK," 删除成功");
}
```


##  发布信息验证 Token
####  1.用户登录签发 JWT
（1）修改 UserController，引入 JwtUtil 修改 login 方法 ，返回 token，昵称，头像等信息
```
@Autowired
private JwtUtil jwtUtil;
/*  用户登陆 */
@RequestMapping(value="/login",method=RequestMethod.POST)
public Result login(@RequestBody Map<String,String> loginMap){

    User user = userService.findByMobileAndPassword(loginMap.get("mobile"),
                             loginMap.get("password"));
    if(user!=null){
        String token = jwtUtil.createJWT(user.getId(),user.getNickname(),"user");
        Map map=new HashMap();
        map.put("token",token);
        map.put("name",user.getNickname());				// 昵称
        map.put("avatar",user.getAvatar())	;				// 头像
        return new Result(true,StatusCode.OK," 登陆成功",map);
    }else{
     return new Result(false,StatusCode.LOGINERROR," 用户名或密码错误");
    }
}
```
（2）测试运行 http://localhost:9008/user/login (POST) ，结果为如下形式
```
{
"flag": true,
"code": 20000,
"message": "登陆成功",
"data": {
    "token":
    "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ODM5ODc0ODk1MDcyNTAxNzYiLCJpYXQiOjE1MjM
    1MDIzMDEsInJvbGVzIjoidXNlciIsImV4cCI6MTUyMzUwMjY2MX0.QH‐
    WMRgIAxHDcYReH7patg7qkcjc4ZuyDbHaIah‐spQ"
},
"name":"小雅",
"avatar":"......."
}
```

####  2. 发布问题
（1）修改 linguochao_qa 工程的 QaApplication，增加 bean
```
@Bean
public JwtUtil jwtUtil(){
    return new util.JwtUtil();
}
```

（2）linguochao_qa 工程配置文件 application.yml 增加配置
```
jwt:
    config:
    key: itdan
```

（3）增加拦截器类 （参考上面的拦截器代码）
（4）增加配置类 ApplicationConfig （参考上面的配置类代码）
（5）修改 ProblemController 的 add 方法
```
@Autowired
private HttpServletRequest request;
/**
* 发布问题
* @param problem
*/
@RequestMapping(method=RequestMethod.POST)
public Result add(@RequestBody Problem problem ){
Claims claims=(Claims)request.getAttribute("user_claims");
    if(claims==null){
        return new Result(false,StatusCode.ACCESSERROR," 无权访问");
    }
    problem.setUserid(claims.getId());
    problemService.add(problem);
    return new Result(true,StatusCode.OK," 增加成功");
}
```








