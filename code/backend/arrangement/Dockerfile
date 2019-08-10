FROM openjdk:8-jre-alpine
COPY arrangement/target/arrangement-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8082 9082
ENV JAVA_OPTS=""
ADD wait /wait
RUN chmod +x /wait
ENTRYPOINT ["sh", "-c", "/wait && java $JAVA_OPTS -jar /app/arrangement-0.0.1-SNAPSHOT.jar"]

