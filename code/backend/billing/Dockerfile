FROM openjdk:8-jre-alpine
COPY billing/target/billing-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8083 9083
ENV JAVA_OPTS=""
ADD wait /wait
RUN chmod +x /wait
ENTRYPOINT ["sh", "-c", "/wait && java $JAVA_OPTS -jar /app/billing-0.0.1-SNAPSHOT.jar"]

