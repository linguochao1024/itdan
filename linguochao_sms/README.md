#  短信服务（阿里云通信）
###  1.阿里云通信简介
阿里云通信（原名--阿里大于）是阿里云旗下产品，融合了三大运营商的通信能力，通过
将传统通信业务和能力与互联网相结合，创新融合阿里巴巴生态内容，全力为中小企业和开
发者提供优质服务阿里大于提供包括短信、语音、流量直充、私密专线、店铺手机号等个性
化服务。通过阿里大于打通三大运营商通信能力，全面融合阿里巴巴生态，以开放 API 及
SDK 的方式向开发者提供通信和数据服务，更好地支撑企业业务发展和创新服务。
###  2.准备工作
（1）在阿里云官网 www.alidayu.com 注册账号
（2）手机下载”阿里云“APP，完成实名认证
（3）登陆阿里云，产品中选择”短信服务“
（4）申请签名
（5）申请模板
（6）创建 accessKey （注意保密！）
（7）充值 
###  3.代码编写
在工程模块 linguochao_sms，pom.xml 引入依赖
```pom
<dependency>
    <groupId>com.aliyun</groupId>
    <artifactId>aliyun-java-sdk-dysmsapi</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.aliyun</groupId>
    <artifactId>aliyun-java-sdk-core</artifactId>
    <version>3.2.5</version>
</dependency>
```

（2）修改 application.yml ，增加配置
```yml
aliyun:
    sms:
        accessKeyId: 不告诉你
        accessKeySecret: 不告诉你
        template_code: SMS_85735065
        sign_name: 不告诉你
```

（3）创建短信工具类 SmsUtil （资源已提供，直接拷贝即可）
```java
/**
* 短信工具类
* @author Administrator
*/
@Component
public class SmsUtil {

// 产品名称 : 云通信短信 API 产品 , 开发者无需替换
static final String product = "Dysmsapi";

// 产品域名 , 开发者无需替换
static final String domain = "dysmsapi.aliyuncs.com";

@Autowired
private Environment env;

// TODO 此处需要替换成开发者自己的 AK( 在阿里云访问控制台寻找 )
/**
* 发送短信
* @param mobile 手机号
* @param template_code 模板号
* @param sign_name 签名
* @param param 参数
* @return
* @throws ClientException
*/
public SendSmsResponse sendSms(String mobile, String template_code, 
String sign_name, String param) throws ClientException {

String accessKeyId =env.getProperty("aliyun.sms.accessKeyId");
String accessKeySecret = env.getProperty("aliyun.sms.accessKeySecret");

// 可自助调整超时时间
System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
System.setProperty("sun.net.client.defaultReadTimeout", "10000");

// 初始化 acsClient, 暂不支持 region 化
ClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId, 
	                accessKeySecret);
IDefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,domain);
IAcsClient acsClient = new DefaultAcsClient(profile);

// 组装请求对象 - 具体描述见控制台 - 文档部分内容
SendSmsRequest request = new SendSmsRequest();

// 必填 : 待发送手机号
request.setPhoneNumbers(mobile);

// 必填 : 短信签名 - 可在短信控制台中找到
request.setSignName(sign_name);

// 必填 : 短信模板 - 可在短信控制台中找到
request.setTemplateCode(template_code);

// 可选 : 模板中的变量替换 JSON 串 , 
        如模板内容为 " 亲爱的 ${name}, 您的验证码为 ${code}" 时 , 此处的值为
request.setTemplateParam(param);
// 选填 - 上行短信扩展码 ( 无特殊需求用户请忽略此字段 )
//request.setSmsUpExtendCode("90997");

// 可选 :outId 为提供给业务方扩展字段 , 最终在短信回执消息中将此值带回给调用者
request.setOutId("yourOutId");

//hint 此处可能会抛出异常，注意 catch
SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
return sendSmsResponse;
}

public QuerySendDetailsResponse querySendDetails(String mobile,StringbizId) 
    throws ClientException {

String accessKeyId =env.getProperty("accessKeyId");
String accessKeySecret = env.getProperty("accessKeySecret");

// 可自助调整超时时间
System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
System.setProperty("sun.net.client.defaultReadTimeout", "10000");

// 初始化 acsClient, 暂不支持 region 化
IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
accessKeyId, accessKeySecret);
DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,domain);
IAcsClient acsClient = new DefaultAcsClient(profile);

// 组装请求对象
QuerySendDetailsRequest request = new QuerySendDetailsRequest();
// 必填 - 号码
request.setPhoneNumber(mobile);
// 可选 - 流水号
request.setBizId(bizId);
// 必填 - 发送日期 支持 30 天内记录查询，格式 yyyyMMdd
SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
request.setSendDate(ft.format(new Date()));
// 必填 - 页大小
request.setPageSize(10L);
// 必填 - 当前页码从 1 开始计数
request.setCurrentPage(1L);
//hint 此处可能会抛出异常，注意 catch
QuerySendDetailsResponse querySendDetailsResponse =
                             acsClient.getAcsResponse(request);
return querySendDetailsResponse;
}
}
```
1. 需求分析
开发短信发送微服务，从 rabbitMQ 中提取消息，调用阿里大于短信接口实现短信发送 。（关于短信阿里大于，我们在前面的电商项目中已经讲解过，故账号申请等环节略过）我们这里实际做的就是消息的消费者

2. 提取队列中的消息
1.  工程搭建
（1）创建工程模块：linguochao_sms，pom.xml 引入依赖
```pom
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

（2）创建 application.yml
```yml
server:
	port: 9009
spring:
    application:
   		name: linguochao‐sms #指定服务名
    rabbitmq:
    	host: 192.168.184.134
```
（3）com.linguochao.sms 包下创建启动类
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
```

2.  消息监听类
（1）创建短信监听类，获取手机号和验证码
```java
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

@Value("${aliyun.sms.template_code}")
private String template_code;			// 模板号

@Value("${aliyun.sms.sign_name}")
private String sign_name;			// 短信签名

@Autowired
private SmsUtil smsUtil;

@RabbitHandler
public void sendSms(Map<String,String> map){

System.out.println(" 手机号："+map.get("mobile"));
System.out.println(" 验证码："+map.get("code"));

try {
smsUtil.sendSms(map.get("mobile"),template_code,  sign_name,
"  { \"code \": \"   "  +   map.get("code")+   "  \" }  "   );
    //{"code":"123456"}
} catch (ClientException e) {
	e.printStackTrace();
}
}
}
```
（2）运行 SmsApplication 类，控制台显示手机号和验证码