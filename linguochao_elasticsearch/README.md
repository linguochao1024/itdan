# 一. ElasticSearch Docker  环境下安装

### 1. 容器的创建与远程连接
##### （1）下载镜像
```
docker pull elasticsearch:5.6.8
```
##### （2）创建容器

```
docker run ‐di ‐‐name=linguochao_elasticsearch ‐p 9200:9200 ‐p 9300:9300  elasticsearch:5.6.8
```
##### （3）浏览器输入地址：
http://192.168.184.134:9200/ 即可看到如下信息

```
{
    "name" : "WmBn0H‐",
    "cluster_name" : "elasticsearch",
    "cluster_uuid" : "2g‐VVbm9Rty7J4sksZNJEg",
    "version" : {
    "number" : "5.6.8",
    "build_hash" : "688ecce",
    "build_date" : "2018‐02‐16T16:46:30.010Z",
    "build_snapshot" : false,
    "lucene_version" : "6.6.1"
},
	"tagline" : "You Know, for Search"
}
```
##### （4）我们修改 demo 的 application.yml

```
spring:
    data:
        elasticsearch:
       		 cluster‐nodes: 192.168.184.135:9300
```
##### （5）运行测试程序，发现会报如下错误

```
NoNodeAvailableException[None of the configured nodes are available:
[{#transport#‐1}{exvgJLR‐RlCNMJy‐hzKtnA}{192.168.184.135}
{192.168.184.135:9300}]
]
at
org.elasticsearch.client.transport.TransportClientNodesService.ensureNodes
AreAvailable(TransportClientNodesService.java:347)
at
org.elasticsearch.client.transport.TransportClientNodesService.execute(Tra
nsportClientNodesService.java:245)
at
org.elasticsearch.client.transport.TransportProxyClient.execute(TransportP
roxyClient.java:59)
```
这是因为 elasticsearch 从 5 版本以后默认不开启远程连接，需要修改配置文件

#####	（6）我们进入容器

```
docker exec ‐it linguochao_elasticsearch /bin/bash
```
此时，我们看到 elasticsearch 所在的目录为/usr/share/elasticsearch ,进入 config 看到了配置文件
elasticsearch.yml
我们通过 vi 命令编辑此文件，尴尬的是容器并没有 vi 命令 ，咋办？我们需要以文件挂载的方式创建容器才行，这样我们就可以通过修改宿主机中的某个文件来实现对容器内配置文件的修改

#####	（7）拷贝配置文件到宿主机
首先退出容器，然后执行命令：

```
docker cp
linguochao_elasticsearch:/usr/share/elasticsearch/config/elasticsearch.yml
/usr/share/elasticsearch.yml
```

#####	（8）停止和删除原来创建的容器

```
docker stop linguochao_elasticsearch
docker rm linguochao_elasticsearch
```

#####	（9）重新执行创建容器命令

```
docker run ‐di ‐‐name=linguochao_elasticsearch ‐p 9200:9200 ‐p 9300:9300 
‐v  /usr/share/elasticsearch.yml : /usr/share/elasticsearch/config/elasticsearch.yml elasticsearch:5.6.8
```

#####	（10）修改/usr/share/elasticsearch.yml 
将 transport.host: 0.0.0.0 前的#去掉后,保存文件退出。其作用是允许任何 ip 地址访问 elasticsearch .开发测试阶段可以这么做，生产环境下指定具体的 IP

#####	（11）重启启动

```
docker restart linguochao_elasticsearch
```
重启后发现重启启动失败了，这时什么原因呢？这与我们刚才修改的配置有关，因为elasticsearch 在启动的时候会进行一些检查，比如最多打开的文件的个数以及虚拟内存区域数量等等，如果你放开了此配置，意味着需要打开更多的文件以及虚拟内存，所以我们还需要系统调优。

#####	（12）系统调优
我们一共需要修改两处
修改/etc/security/limits.conf ，追加内容

```
* soft nofile 65536
* hard nofile 65536
```
nofile 是单个进程允许打开的最大文件个数 
soft nofile 是软限制 
hard nofile 是硬限制

修改/etc/sysctl.conf，追加内容
```
vm.max_map_count=655360
```
限制一个进程可以拥有的 VMA(虚拟内存区域)的数量
执行下面命令 修改内核参数马上生效
```
sysctl ‐p
```

#####	（13）重新启动虚拟机，再次启动容器，发现已经可以启动并远程访问


### 2. IK  分词器安装
#####	（1）快捷键 alt+p 进入 sftp , 将 ik 文件夹上传至宿主机

```
sftp> put -r d:\setup\ik
```
#####	（2）在宿主机中将 ik 文件夹拷贝到容器内 /usr/share/elasticsearch/plugins 目录下。

```
docker cp ik linguochao_elasticsearch : /usr/share/elasticsearch/plugins/
```
#####	（3）重新启动，即可加载 IK 分词器

```
docker restart linguochao_elasticsearch
```

### 3.Head  插件安装
#####	（1）修改/usr/share/elasticsearch.yml ,添加允许跨域配置

```
http.cors.enabled: true
http.cors.allow‐origin: "*"
```
#####	（2）重新启动 elasticseach 容器

#####	（3）下载 head 镜像（此步省略）

```
docker pull mobz/elasticsearch-head:5
```
#####	（4）创建 head 容器

```
docker run -di --name=myhead -p 9100:9100 mobz/elasticsearch-head:5
```

### 4.logstash  安装
#####	（1）下载 logstash 镜像

```
docker pull logstash
```

#####	（2）创建 logstash 目录

```
mkdir /usr/local/logstash
```

#####	（3）在 logstash 目录下建立 Dockerfile 文件，内容如下：

```
FROM logstash
CMD [“-f”,”/usr/local/logstash/mysql.conf”]
```
FROM logstash：意思是镜像构建在 logstash 基础之上
CMD [“-f”,”/usr/local/logstash/mysql.conf”]：代表在 docker run 运行镜像，容器内
部启动过程中，添加参数 -f /usr/local/logstash/mysql.conf。

#####	（4）构建新的 logstash 镜像

```
docker build -t logstash_new .
```
构建后查询 logstash_new 镜像是否构建成功。

#####	（5）以目录挂载形式启动新的 logstash 镜像

```
docker run -di --name=logstash -v /usr/local/logstash:/usr/local/logstash
logstash_new
```

#####	（6）把之前在 window 的 mysql.conf 和 mysql 驱动文件上传到宿主机的
/usr/local/logstash 目录下面

#####	（7）修改 mysql.conf 内容：

```shell
input {
    jdbc {
        # mysql jdbc connection string to our backup databse
        jdbc_connection_string =>
        "jdbc:mysql://192.168.66.134:3306/linguochao_article?characterEncoding=UTF8"
        # the user we wish to excute our statement as
        jdbc_user => "root"
        jdbc_password => "123456"
        # the path to our downloaded jdbc driver
        jdbc_driver_library => "/usr/local/logstash/mysql-connector-java-5.1.46.jar"
        # the name of the driver class for mysql
        jdbc_driver_class => "com.mysql.jdbc.Driver"
        jdbc_paging_enabled => "true"
        jdbc_page_size => "50000"
        #以下对应着要执行的 sql 的绝对路径。
        #statement_filepath => ""
        statement => "select id,title,content from tb_article"
        #定时字段 各字段含义（由左至右）分、时、天、月、年，全部为*默认含义为每分
        钟都更新（测试结果，不同的话请留言指出）
        schedule => "* * * * *"
    }
    }
    output {
        elasticsearch {
        #ESIP 地址与端口
        hosts => "192.168.66.133:9200"
        #ES 索引名称（自己定义的）
        index => "linguochao"
        #自增 ID 编号
        document_id => "%{id}"
        document_type => "article"
    }
    stdout {
        #以 JSON 格式输出
        codec => json_lines
    }
}
```

##### （8）重启 logstash 容器

```
docker restart logstash
```

##### （9）查看 logstash 容器日志，看是否 1 分钟同步一次数据到 elasticsearch

```
docker logs -f --tail=30 logstash
```

# 二.搜索微服务开发
### 1.模块搭建
#####  1）pom.xml 导入依赖
```pom
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-elasticsearch</artifactId>
</dependency>
<dependency>
    <groupId>com.linguochao</groupId>
    <artifactId>linguochao_common</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
#####  2）application.yml
```yml
server:
    port: 9007
spring:
    application:
  	  name: linguochao‐search #指定服务名
    data:
        elasticsearch:
        	cluster‐nodes: 127.0.0.1:9300
```
#####  3）创建包 com.linguochao.search ，包下创建启动类
```java
@SpringBootApplication
public class SearchApplication {
    public static void main(String[] args) {
   	 SpringApplication.run(SearchApplication.class, args);
    }
    @Bean
    public IdWorker idWorkker(){
  	  return new IdWorker(1, 1);
    }
}	
```

###  2.文章搜索
实体类
```java
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;

@Document(indexName = "linguochao",type = "article")
public class Article implements Serializable{

    @Id
    private String id;

    //是否索引,就是看该域是否能被搜索
    //是否分词,表示搜索的时候是整体匹配还是单词匹配
    //是否存储,就是是否在页面上显示
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;

	private String columnid;		//专栏ID
	private String userid;		//用户ID
	private String title;			//标题
	private String content;		//文章正文
	private String image;		//文章封面
	private java.util.Date createtime;//发表日期
	private java.util.Date updatetime;//修改日期
	private String ispublic;		//是否公开
	private String istop;			//是否置顶
	private Integer visits;		//浏览量
	private Integer thumbup;		//点赞数
	private Integer comment;	//评论数
	private String state;			//审核状态
	private String channelid;		//所属频道
	private String url;			//URL
	private String type;			//类型
```

Controller
在 ArticleSearchController 添加方法
```java
public class ArticleController {
    @RequestMapping(value="/search/{keywords}/{page}/{size}",method=RequestMethod.GET)
    public Result search(@PathVariable String keywords, @PathVariable int page,
          @PathVariable int size){
    Page<Article> articlePage = articleSearchService.findByTitleLike(keywords,page,size);
    return new Result(true, StatusCode.OK, " 查询成功",
    new PageResult<Article>(articlePage.getTotalElements(),ticlePage.getContent()));
    }
}
```

Service
在 ArticleSearchService 添加方法

```java
public class ArticleService {
/**
 *  增加文章
 * @param article
 */
public void add(Article article){
 	article.setId( idWorker.nextId()+ "");
    articleDao.save(article);
}

/**
 * 根据关键字查询文章
 * @param keywords
 * @param page
 * @param size
 * @return
 */
public PageResult findByKeywords(String keywords,int page,int size){
    PageRequest pageRequest=PageRequest.of(page-1,size);
    Page<Article> pageList = articleDao.findByTitleOrContent(keywords, keywords, pageRequest);
  return new PageResult<>(pageList.getTotalElements(),pageList.getContent());
}
}
```

Dao
在 ArticleSearchRepository 添加方法

```java
public interface ArticleDao extends ElasticsearchRepository<Article,String>{
    public Page<Article> findByTitleOrContentLike(String tilte, String content, Pageable pageable);
}
```

测试
在浏览器访问搜素方法
