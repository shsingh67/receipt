FROM openjdk:17-jdk-slim AS build
RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/receipt-0.0.1-SNAPSHOT.jar ./receipt-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/receipt-0.0.1-SNAPSHOT.jar"]