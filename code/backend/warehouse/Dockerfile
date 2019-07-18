FROM openjdk:8-jre-alpine
ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8
VOLUME /tmp
ADD target/warehouse-0.0.1-SNAPSHOT.jar warehouse-0.0.1-SNAPSHOT.jar
EXPOSE 8084 9084
ENTRYPOINT ["java","-jar","warehouse-0.0.1-SNAPSHOT.jar"]
