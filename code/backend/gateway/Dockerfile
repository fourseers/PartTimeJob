FROM openjdk:8-jdk-alpine AS builder
WORKDIR /app
ADD pom.xml /app
RUN mkdir gateway && \
    apk add maven
ADD gateway/pom.xml /app/gateway
RUN mvn verify clean --fail-never && \
    cd gateway && \
    mvn verify clean --fail-never

ADD . /app
RUN mvn -pl .,common,gateway clean package -Dmaven.test.skip=true
RUN ls /app/gateway/target

FROM openjdk:8-jre-alpine
COPY --from=builder /app/gateway/target/gateway-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8079 9079
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/gateway-0.0.1-SNAPSHOT.jar"]

