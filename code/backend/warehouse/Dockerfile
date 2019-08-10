FROM openjdk:8-jdk-alpine AS builder
WORKDIR /app
ADD pom.xml /app
RUN mkdir warehouse && \
    apk add maven
ADD warehouse/pom.xml /app/warehouse
RUN mvn verify clean --fail-never && \
    cd warehouse && \
    mvn verify clean --fail-never

ADD . /app
RUN mvn -pl .,common,warehouse clean package -Dmaven.test.skip=true
RUN ls /app/warehouse/target

FROM openjdk:8-jre-alpine
COPY --from=builder /app/warehouse/target/warehouse-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8084 9084
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/warehouse-0.0.1-SNAPSHOT.jar"]

