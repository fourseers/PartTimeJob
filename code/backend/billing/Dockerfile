FROM openjdk:8-jdk-alpine AS builder
WORKDIR /app
ADD pom.xml /app
RUN mkdir billing && \
    apk add maven
ADD billing/pom.xml /app/billing
RUN mvn verify clean --fail-never && \
    cd billing && \
    mvn verify clean --fail-never

ADD . /app
RUN mvn -pl .,common,billing clean package -Dmaven.test.skip=true
RUN ls /app/billing/target

FROM openjdk:8-jre-alpine
COPY --from=builder /app/billing/target/billing-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8083 9083
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/billing-0.0.1-SNAPSHOT.jar"]

