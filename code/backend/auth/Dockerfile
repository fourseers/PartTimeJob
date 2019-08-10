FROM openjdk:8-jdk-alpine AS builder
WORKDIR /app
ADD pom.xml /app
RUN mkdir auth && \
    apk add maven
ADD auth/pom.xml /app/auth
RUN mvn verify clean --fail-never && \
    cd auth && \
    mvn verify clean --fail-never

ADD . /app
RUN mvn -pl .,common,auth clean package -Dmaven.test.skip=true
RUN ls /app/auth/target

FROM openjdk:8-jre-alpine
COPY --from=builder /app/auth/target/auth-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8080 9080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/auth-0.0.1-SNAPSHOT.jar"]

