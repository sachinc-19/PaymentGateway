FROM maven:3.9-eclipse-temurin-23 AS build
WORKDIR /PayoutEngine
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jdk-alpine
WORKDIR /PayoutEngine
COPY --from=build /PayoutEngine/target/PayoutEngine-0.0.1-SNAPSHOT.jar payoutengine.jar
EXPOSE 8080
CMD ["java", "-jar", "payoutengine.jar"]