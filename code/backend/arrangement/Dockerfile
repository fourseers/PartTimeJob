FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/arrangement-0.0.1-SNAPSHOT.jar arrangement-0.0.1-SNAPSHOT.jar
EXPOSE 8082 9082
ENTRYPOINT ["java","-jar","arrangement-0.0.1-SNAPSHOT.jar"]
