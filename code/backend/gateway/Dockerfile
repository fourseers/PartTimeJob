FROM openjdk:8-jre-alpine
COPY gateway/target/gateway-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8079 9079
ENV JAVA_OPTS=""
ADD wait /wait
RUN chmod +x /wait
ENTRYPOINT ["sh", "-c", "/wait && java $JAVA_OPTS -jar /app/gateway-0.0.1-SNAPSHOT.jar"]

