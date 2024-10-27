FROM maven:latest AS build

WORKDIR /app

COPY . .

RUN mvn clean package

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/configserver-0.0.1-SNAPSHOT.jar configserver-0.0.1-SNAPSHOT.jar

EXPOSE 8071

ENTRYPOINT ["java", "-jar", "configserver-0.0.1-SNAPSHOT.jar"]