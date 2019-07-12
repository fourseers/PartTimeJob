FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server-0.0.1-SNAPSHOT.jar
EXPOSE 8761 9761
ENTRYPOINT ["java","-jar","eureka-server-0.0.1-SNAPSHOT.jar"]
