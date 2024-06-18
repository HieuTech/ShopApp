#Stage 1: Build Project

#FROM maven:3.8.6-openjdk-17 AS build
#WORKDIR /app
#
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean install


#Stage 2: Package

FROM eclipse-temurin:17-jdk
#thư mục lưu trữ trên Linux
VOLUME /tmp
#Tên biến
ARG JAR_FILE=target/*.jar
#Copy thư mục vào
COPY ${JAR_FILE} ShopApp.jar
ENTRYPOINT ["java","-jar","/ShopApp.jar"]

EXPOSE 8888

