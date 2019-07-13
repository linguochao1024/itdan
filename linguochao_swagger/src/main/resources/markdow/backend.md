# 文档使用说明书

## 核心功能

- **文档说明**：包括接口地址、类型、请求示例、请求参数、响应示例、响应参数、响应码等信息。

- **在线调试**：自动解析当前接口参数,同时包含表单验证，调用参数可返回接口响应内容、headers、Curl请求命令实例、响应时间、响应状态码等信息。

- **离线文档**：根据标准规范，生成的在线markdown离线文档，可以进行拷贝生成markdown接口文档，通过其他第三方markdown转换工具转换成html或pdf

- **全局搜索**：搜索关键字主要包括：**URL地址、接口说明、方法类型、接口描述**

- **全局参数**：目前全局参数功能主要提供两种参数类型：**query(表单)、header(请求头)**



## 使用方法

- **接口文档地址**：{ip}:{port}/doc.html

- **登录用户名和密码**：找后端人员。

- **全局参数**：
```
1.Content-Type：application/json
```
- **个性化设置**：勾选下面三个选项：菜单Api地址显示，分组tag显示dsecription说明属性，启用SwaggerBootstrapUi提供的增强功能



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
@EnableSwagger2
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