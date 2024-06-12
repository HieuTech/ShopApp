#Stage 1: Build Project

#FROM maven:3.8.6-eclipse-temurin_17 AS build
#WORKDIR /app
#
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests




#Stage 2: Package

FROM eclipse-temurin:17-jdk
#thư mục lưu trữ trên Linux
VOLUME /tmp
#Tên biến
ARG JAR_FILE=target/*.jar
#Copy thư mục vào
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080