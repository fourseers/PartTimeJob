FROM openjdk:8-jre-alpine
COPY warehouse/target/warehouse-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8084 9084
ENV JAVA_OPTS=""
ADD wait /wait
RUN chmod +x /wait
ENTRYPOINT ["sh", "-c", "/wait && java $JAVA_OPTS -jar /app/warehouse-0.0.1-SNAPSHOT.jar"]

