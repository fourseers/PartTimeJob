FROM openjdk:8-jdk-alpine AS builder
WORKDIR /app
ADD pom.xml /app
RUN mkdir eureka-server && \
    apk add maven
ADD eureka-server/pom.xml /app/eureka-server
RUN mvn verify clean --fail-never && \
    cd eureka-server && \
    mvn verify clean --fail-never

ADD . /app
RUN mvn -pl .,common,eureka-server clean package -Dmaven.test.skip=true
RUN ls /app/eureka-server/target

FROM openjdk:8-jre-alpine
COPY --from=builder /app/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8761 9761
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/eureka-server-0.0.1-SNAPSHOT.jar"]

