FROM openjdk:8-jre-alpine
ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8
VOLUME /tmp
ADD target/gateway-0.0.1-SNAPSHOT.jar gateway-0.0.1-SNAPSHOT.jar
EXPOSE 8079 9079
ENTRYPOINT ["java","-jar","gateway-0.0.1-SNAPSHOT.jar"]
