#!/bin/sh

url="localhost:3306"
username="root"
password="Lin940810"

java -Xms256m -Xmx256m -Dapollo_profile=github -Dspring.datasource.url=jdbc:mysql://${url}/ApolloConfigDB?characterEncoding=utf8 -Dspring.datasource.username=${username} -Dspring.datasource.password=${password} -Dlogging.file=./logs/apollo-configservice.log -Dserver.port=8081 -jar apollo-configservice-1.3.0.jar &
java -Xms256m -Xmx256m -Dapollo_profile=github -Dspring.datasource.url=jdbc:mysql://${url}/ApolloConfigDB?characterEncoding=utf8 -Dspring.datasource.username=${username} -Dspring.datasource.password=${password} -Dlogging.file=./logs/apollo-adminservice.log -Dserver.port=8091 -jar apollo-adminservice-1.3.0.jar &
java -Xms256m -Xmx256m -Dapollo_profile=github,auth -Ddev_meta=http://localhost:8091/ -Dserver.port=8071 -Dspring.datasource.url=jdbc:mysql://${url}/ApolloPortalDB?characterEncoding=utf8 -Dspring.datasource.username=${username} -Dspring.datasource.password=${password} -Dlogging.file=./logs/apollo‐portal.log  -jar apollo-portal-1.3.0.jar


#java -Xms256m -Xmx256m -Dapollo_profile=github -Dspring.datasource.url=jdbc:mysql://localhost:3306/ApolloConfigDB?characterEncoding=utf8 -Dspring.datasource.username=root -Dspring.datasource.password=Lin940810 -Dlogging.file=./logs/apollo-configservice.log -Dserver.port=8081 -jar apollo-configservice-1.3.0.jar
#java -Xms256m -Xmx256m -Dapollo_profile=github -Dspring.datasource.url=jdbc:mysql://localhost:3306/ApolloConfigDB?characterEncoding=utf8 -Dspring.datasource.username=root -Dspring.datasource.password=Lin940810 -Dlogging.file=./logs/apollo-adminservice.log -Dserver.port=8091 -jar apollo-adminservice-1.3.0.jar
#java -Xms256m -Xmx256m -Dapollo_profile=github,auth -Ddev_meta=http://localhost:8081/ -Dserver.port=8071 -Dspring.datasource.url=jdbc:mysql://localhost:3306/ApolloPortalDB?characterEncoding=utf8 -Dspring.datasource.username=root -Dspring.datasource.password=Lin940810 -Dlogging.file=.\logs\apollo‐portal.log -jar apollo-portal-1.3.0.jar
