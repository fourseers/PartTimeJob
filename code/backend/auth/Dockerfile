FROM openjdk:8-jre-alpine
COPY auth/target/auth-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8080 9080
ENV JAVA_OPTS=""
ADD wait /wait
RUN chmod +x /wait
ENTRYPOINT ["sh", "-c", "/wait && java $JAVA_OPTS -jar /app/auth-0.0.1-SNAPSHOT.jar"]

