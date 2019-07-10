FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/gateway-0.0.1-SNAPSHOT.jar gateway-0.0.1-SNAPSHOT.jar
EXPOSE 8079 9079
ENTRYPOINT ["java","-jar","gateway-0.0.1-SNAPSHOT.jar"]
