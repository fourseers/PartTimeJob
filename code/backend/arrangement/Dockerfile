FROM openjdk:8-jre-alpine
ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8
VOLUME /tmp
ADD target/arrangement-0.0.1-SNAPSHOT.jar arrangement-0.0.1-SNAPSHOT.jar
EXPOSE 8082 9082
ENTRYPOINT ["java","-jar","arrangement-0.0.1-SNAPSHOT.jar"]
