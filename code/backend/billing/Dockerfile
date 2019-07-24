FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/billing-0.0.1-SNAPSHOT.jar billing-0.0.1-SNAPSHOT.jar
EXPOSE 8083 9083
ENTRYPOINT ["java","-jar","billing-0.0.1-SNAPSHOT.jar"]
