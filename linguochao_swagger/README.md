# 后端开发手册

## 一.Maven中引入Jar包

由于是springfox-swagger的增强UI包,所以基础功能依然依赖Swagger,springfox-swagger的jar包必须引入

 ```
<dependency>
 <groupId>io.springfox</groupId>
 <artifactId>springfox-swagger2</artifactId>
 <version>2.9.2</version>
</dependency>
```
然后引入SwaggerBootstrapUi的jar包
```
<dependency>
  <groupId>com.github.xiaoymin</groupId>
  <artifactId>swagger-bootstrap-ui</artifactId>
  <version>1.9.3</version>
</dependency>
```

## 二.编写Swagger2Config配置文件
Swagger2Config配置文件如下：
```
@Configuration
@Configuration
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Value("${swagger2.enable}")
    private boolean swagger2Enable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.medcn.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("客户管理平台接口")
                .description("实时文档，可在线调试")
                .termsOfServiceUrl("http://10.0.0.254:4999/web/#/12?page_id=1825")
                .version("1.0")
                .build();
    }
}

```
## 三.访问地址
swagger-bootstrap-ui默认访问地址是：http://${host}:${port}/doc.html

## 四.shiro权限放行

Shiro的相关配置实例如下：
```
        /swagger-resources = anon
        /v2/api-docs = anon
        /v2/api-docs-ext = anon
        /doc.html = anon
        /webjars/** = anon
```
SpringBoot中访问doc.html报404的解决办法

实现SpringBoot的WebMvcConfigurer接口，添加相关的ResourceHandler,代码如下：
```
@SpringBootApplication
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerBootstrapUiDemoApplication  implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
```

## 五.自定义文档

可以在当前项目中添加一个文件夹，文件夹中存放.md格式的markdown文件,每个.md文档代表一份自定义文档说明

注意：自定义文档说明必须以.md结尾的文件,其他格式文件会被忽略

每个.md文件中，swagger-bootstrap-ui允许一级(h1)、二级(h2)、三级(h3)标题作为最终的文档标题

如果没有按照一级(h1)、二级(h2)、三级(h3)来设置标题,默认标题会是文件名称，如图上的api2.md

在SpringBoot环境中,首先需要在application.yml或者application.properties配置文件中配置自定义文档目录
```
swagger:
  markdowns: classpath:markdown/*
```

然后在Swagger的配置文件中启用@EnableSwaggerBootstrapUi注解
```
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfiguration {
    //more...
    
}
```
## 六.访问权限控制

#### 1.分环境配置,通过配置文件开启swagger
application.properties或者application.yml 配置文件中增加swagger2.enable属性
```
@Configuration
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Value("${swagger2.enable}")
    private boolean swagger2Enable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.medcn.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }
```
#### 2.访问页面加权控制
需在相应的application.properties或者application.yml中配置如下：
```
## 开启Swagger的Basic认证功能,默认是false
swagger.basic.enable=true
## Basic认证用户名
swagger.basic.username=zhangsan
## Basic认证密码
swagger.basic.password=123
```

## 七.分组配置

需要重新定义一个Docket的bean，在其中指定另外接口层的位置即可：

```
@Configuration
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Value("${swagger2.enable}")
    private boolean swagger2Enable;


    @Bean
    public Docket RestApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .apiInfo(apiInfo())
                .groupName("系统接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.medcn.web.controller.sys"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket RestApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .apiInfo(apiInfo())
                .groupName("统计接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.medcn.web.controller.stats"))
                .paths(PathSelectors.any())
                .build();
    }
```
也可以通过path来指定范围
``` 
  .paths(PathSelectors.regex("/other.*"))
```

## 八.资源
swagger-ui地址: https://gitee.com/xiaoym/swagger-bootstrap-ui
swagger-ui文档:https://doc.xiaominfo.com/guide/


# API 技巧

## 一.泛型类返回处理

1.继承BaseResponse基类
```
public class MessageController extends BaseResult {
```

2.有返回值 用BaseResult<T>泛型类
```
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录", notes = "单位号登录", httpMethod = "POST")
    public BaseResponse<Principal> login(@Validated @RequestBody LoginParamDTO loginParamDTO, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        String userName = loginParamDTO.getUserName();
        String password = loginParamDTO.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, true);
        token.setHost(request.getRemoteHost());
        subject.login(token);
        Principal principal = getCurrentUser();
        return success(principal);
    }

```

3. 只针对有返回值的,无返回值,response =BaseNotArgument.class
```
    @GetMapping(value = "/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "GET", response =BaseNotArgument.class)
    public String loginOut() {
        SecurityUtils.getSubject().logout();
        return success();
    }
```

## 二.动态字段注释
问题:
传递动态的Map或者JSONObject作为入参接收对象,这样能以JSON的方式对入参进行扩展,方便业务功能的扩展开发
但是,如果使用以上的方式,我们用Swagger框架来生成文档时,Swagger并不知道我们需要传递那些字段属性,因为它是声明式的
只有在定义了相关类或者参数注释说明后,Swagger才可以帮助我们生成文档

```
@PostMapping("/createOrder422")
@ApiOperation(value = "jdk-Map-动态创建显示参数")
public Rest<Map> createOrder12232(@RequestBody Map map){
    Rest<Map> r=new Rest<>();
    r.setData(map);
    return r;
}

@PostMapping("/createOrder423")
@ApiOperation(value = "jdk-JSONObject-动态创建显示参数")
public Rest<JSONObject> createOrder12232(@RequestBody JSONObject json){
    Rest<JSONObject> r=new Rest<>();
    r.setData(json);
    return r;
}
```

解决方案:
针对Map、JSONObject等动态类型可通过自定义注解@ApiOperationSupport或者@DynamicParameters来增加参数的字段说明,解决不想写实体类的烦恼,但是又无文档的困扰.
```
@PostMapping("/createOrder421")
@ApiOperation(value = "fastjson-JSONObject-动态创建显示参数")
@ApiOperationSupport(params = @DynamicParameters(name = "CreateOrderModel",properties = {
        @DynamicParameter(name = "id",value = "注解id",example = "X000111",required = true,dataTypeClass = Integer.class),
        @DynamicParameter(name = "name",value = "订单编号",required = false)
}))
public Rest<JSONObject> createOrder12222(@RequestBody JSONObject jsonObject){
    Rest<JSONObject> r=new Rest<>();
    r.setData(jsonObject);
    return r;
}

@PostMapping("/createOrder422")
@ApiOperation(value = "jdk-Map-动态创建显示参数")
@DynamicParameters(name = "CreateOrderMapModel",properties = {
            @DynamicParameter(name = "id",value = "注解id",example = "X000111",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "name",value = "订单编号"),
            @DynamicParameter(name = "name1",value = "订单编号1"),
            @DynamicParameter(name = "orderInfo",value = "订单信息",dataTypeClass = Order.class),
    })
public Rest<Map> createOrder12232(@RequestBody Map map){
    Rest<Map> r=new Rest<>();
    r.setData(map);
    return r;
}
```


## 三.接口排序:position 属性
ui支持了接口排序功能，例如一个注册功能主要包含了多个步骤,
可以根据swagger-bootstrap-ui提供的接口排序规则实现接口的排序，step化接口操作，方便其他开发者进行接口对接
```
@Api(value = "手机相关接口", position = 3,description = "手机管理相关")
public class MobileController extends BaseController {

    @PostMapping(value = "/captcha")
    @ApiOperation(value = "发送验证码", notes = "给对应手机号发送验证码", httpMethod = "POST", position = 1,response = BaseResult.class)
    public String getCode(@Validated @RequestBody GetCodeReq params) {
        String msg = appUserService.sendCaptcha(params.getMobile());
        return msg;
    }
    @GetMapping(value = "/admin/captcha")
    @ApiOperation(value = "发送验证码", notes = "给管理员手机发送验证码", httpMethod = "GET", position = 3,response = BaseResult.class)
    public String getNewAdminCaptcha() {
        Principal principal = SubjectUtils.getCurrentUser();
        return appUserService.sendCaptcha(principal.getAdminMobile());
    }
```

## 四.tags 属性
@Api注解放在类上面，这里的value是没用的，tags表示该controller的介绍,这样文档就不会显示控制器的名称,只显示tags里的值
```
@Api(value = "登录",position = 2, description = "登录",tags="登录相关")
```
多个控制器可以用相同的tags,这样接口文档会将接口合并展示


