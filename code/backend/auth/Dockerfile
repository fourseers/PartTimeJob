FROM openjdk:8-jre-alpine
ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8
VOLUME /tmp
ADD target/auth-0.0.1-SNAPSHOT.jar auth-0.0.1-SNAPSHOT.jar
EXPOSE 8080 9080
ENTRYPOINT ["java","-jar","auth-0.0.1-SNAPSHOT.jar"]
