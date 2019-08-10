FROM openjdk:8-jre-alpine
COPY eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8761 9761
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/eureka-server-0.0.1-SNAPSHOT.jar"]