## 一.安装环境
### 1. 安装jdk
自行安装

### 2.安装tomcat
```
mkdir /usr/local/solr && cd /usr/local/solr

使用rz上传“apache-tomcat-8.5.28.tar.gz”

# 解压
tar -xzvf apache-tomcat-8.5.28.tar.gz

# 删除
rm -rf apache-tomcat-8.5.28.tar.gz 

# 重命名
mv apache-tomcat-8.5.28 /usr/local/solr/tomcat-solr
```
### 3.修改tomcat端口号
```
cd /usr/local/solr/tomcat-solr/conf
vi server.xml

#server.xml需要修改3个地方的端口号
#（server port（第22行）、http port（第69行）、connector port（第116行））；
#与其它tomcat的不冲突就好，端口依次可以为(8008,8088,8018)


# 启动
/usr/local/solr/tomcat-solr/bin/startup.sh

# 测试
http://192.168.12.131:8088
```
## 二.安装solr
### 1.上传并解压
```
#上传“solr-4.10.3.tgz.tgz”
cd /usr/local/solr
使用rz上传“solr-4.10.3.tgz.tgz”

# 解压
tar -xzvf solr-4.10.3.tgz.tgz

# 删除
rm -rf solr-4.10.3.tgz.tgz
```
### 2.部署到tomcat
```
# 停止之前运行的tomcat
/usr/local/solr/tomcat-solr/bin/shutdown.sh

# 删除tomcat-solr的webapps文件夹所有文件，并创建solr文件夹
cd tomcat-solr/webapps/
rm -rf *
mkdir solr

# 复制solr解压目录的solr.war到tomcat-solr中
cd /usr/local/solr/solr-4.10.3/example/webapps/
cp solr.war /usr/local/solr/tomcat-solr/webapps/solr/

# 解压solr.war
cd /usr/local/solr/tomcat-solr/webapps/solr/
jar -xvf solr.war 


# 删除war包
rm -rf solr.war

# 把solr-4.10.3/example/lib/ext目录下的所有的jar包，添加到solr工程中
cd /usr/local/solr/solr-4.10.3/example/lib/ext/
cp * /usr/local/solr/tomcat-solr/webapps/solr/WEB-INF/lib/


# 加入配置文件到solr工程中
cd /usr/local/solr/solr-4.10.3/example/resources/
mkdir /usr/local/solr/tomcat-solr/webapps/solr/WEB-INF/classes/
cp * /usr/local/solr/tomcat-solr/webapps/solr/WEB-INF/classes/
```

### 3.创建solrhome
```
# /example/solr目录就是一个solrhome，复制此目录到/usr/local/solr/solrhome
cd /usr/local/solr/solr-4.10.3/example/
cp -r solr  /usr/local/solr/solrhome
```

### 4.关联solr及solrhome(修改solr工程的web.xml文件)
```
# 修改solr工程的web.xml文件
vi /usr/local/solr/tomcat-solr/webapps/solr/WEB-INF/web.xml
```
修改如下内容：
1. 删除注释（<env-entry>之上的第一个注释 和 </env-entry>之下的第一个注释）
2. 修改第43行，指定solrhome地址(/usr/local/solr/solrhome)


### 5.开启
```
# 启动
/usr/local/solr/tomcat-solr/bin/startup.sh

# 动态查看tomcat启动日志
tail -f /usr/local/solr/tomcat-solr/logs/catalina.out
```
请求地址：http://localhost:8088/solr/

## 三.配置solr
### 1.配置ik分词器
```
# 停止solr 的tomcat
/usr/local/solr/tomcat-solr/bin/shutdown.sh

# 上传ik中文分词器IKAnalyzer2012FF_u1.jar包
cd /usr/local/solr/tomcat-solr/webapps/solr/WEB-INF/lib/
使用rz 上传“IKAnalyzer2012FF_u1.jar”

# 上传IKAnalyzer的配置文件和自定义词典和停用词词典到tomcat服务
cd ../classes
使用rz 上传“ext.dic”、“IKAnalyzer.cfg.xml”、“stopword.dic”

# 在schema.xml配置中文分词器；不用配置，在 配置业务字段 的时候一起配置即可。现在不配置
```
### 2.配置schema.xml业务相关Field
```
cd /usr/local/solr/solrhome/collection1/conf/

# 备份（重命名）schema.xml
mv schema.xml schema.xml.bak

# 备份（重命名）solrconfig.xml
mv solrconfig.xml solrconfig.xml.bak

# 使用rz在windows上传“schema.xml”和“solrconfig.xml”
# 重启tomcat
/usr/local/solr/tomcat-solr/bin/startup.sh
```







