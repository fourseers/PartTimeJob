FROM openjdk:8-jre-alpine
COPY config-server/target/config-server-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8888 9888
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/config-server-0.0.1-SNAPSHOT.jar"]
