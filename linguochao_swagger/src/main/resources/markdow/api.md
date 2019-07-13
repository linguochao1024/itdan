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


