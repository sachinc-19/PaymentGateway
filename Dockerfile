FROM maven:3.9.9-amazoncorretto-8-al2023 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:23
WORKDIR /PayoutEngine
COPY --from=build ./target/PayoutEngine-0.0.1-SNAPSHOT.jar payoutengine.jar
EXPOSE 8080
CMD ["java", "-jar", "payoutengine.jar"]