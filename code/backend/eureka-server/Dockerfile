FROM openjdk:8-jre-alpine
COPY eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8761 9761
ENV JAVA_OPTS=""
ADD wait /wait
RUN chmod +x /wait
ENTRYPOINT ["sh", "-c", "/wait && java $JAVA_OPTS -jar /app/eureka-server-0.0.1-SNAPSHOT.jar"]
