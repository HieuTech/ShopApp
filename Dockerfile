#Stage 1: Build Project

FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean install


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

#Create new repository in Docker Hub
#docker buildx build --platform linux/amd64 -t hieutech/aws-deploy:1.0 .
##tên image:tag local phải trùng với repository username/

#docker push hieutech/aws-deploy:1.0


###EC2 Terminal
#sudo yum update -y
#sudo yum install -y docker
#sudo service docker start

#sudo docker run -d -p 80:8080 hieutech/aws-deploy:1.0

#Test on EC2 public IP