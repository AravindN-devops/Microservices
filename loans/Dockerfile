FROM maven:latest AS build

WORKDIR /app

COPY . .

RUN mvn clean package

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/loans-0.0.1-SNAPSHOT.jar loans-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "loans-0.0.1-SNAPSHOT.jar"]