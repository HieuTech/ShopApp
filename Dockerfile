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

#Create new repository in Docker Hub
#docker buildx build --platform linux/amd64 -t hieutech/aws-deploy:1.0 .
##tên image:tag local phải trùng với repository username/

#docker push hieutech/aws-deploy:1.0


#cd vao thư mục project bao gồm file pem
#chmod 400  yourfileawsprivate.pem
#ssh -i yourfileawsprivate.pem ec2-user@12.127.52.22



###EC2 Terminal
#sudo su -
#sudo yum update -y

#sudo yum -y install maven
#mvn -v
#java -version

###Install GIT
#sudo yum -y install git
#git --version
#git config --global user.name "HieuTech"
#git config --global user.email "rosasuong.tech@gmail.com"

###Install Jenkins
#sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
 #  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key

 #echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" \
 #  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
 #  /etc/apt/sources.list.d/jenkins.list > /dev/null

 #sudo apt-get update
 #sudo apt-get install jenkins
#sudo systemctl enable jenkins
#sudo systemctl start jenkins
#sudo systemctl status jenkins
#------Jenkin common port is 8080 -> change server port to another.

#sudo yum install -y docker
#sudo service docker start

#sudo docker run -d -p 80:8080 hieutech/aws-deploy:1.0

#Test on EC2 public IP