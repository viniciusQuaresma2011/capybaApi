#FROM maven:3.6.1-jdk-11 AS build
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -f pom.xml clean package -DskipTests
#FROM openjdk:11
#COPY --from=build /workspace/target/*.jar app.jar
FROM ubuntu:latest AS build



RUN apt-get update
RUN apt-get install openjdk-11-jdk -y

RUN apt-get clean && \
    apt-get autoclean && \
    apt-get autoremove -y && \
    rm -rf /var/lib/cache/* && \
    rm -rf /var/lib/log/*

COPY . .
RUN apt-get install maven -y

RUN mvn clean install

FROM openjdk:11

COPY --from=build /workspace/target/*.jar app.jar


EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
