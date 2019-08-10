FROM openjdk:8-jdk-alpine AS builder
WORKDIR /app
ADD pom.xml /app
RUN mkdir arrangement && \
    apk add maven
ADD arrangement/pom.xml /app/arrangement
RUN mvn verify clean --fail-never && \
    cd arrangement && \
    mvn verify clean --fail-never

ADD . /app
RUN mvn -pl .,common,arrangement clean package -Dmaven.test.skip=true
RUN ls /app/arrangement/target

FROM openjdk:8-jre-alpine
COPY --from=builder /app/arrangement/target/arrangement-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8082 9082
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/arrangement-0.0.1-SNAPSHOT.jar"]

