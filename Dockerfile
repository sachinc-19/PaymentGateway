FROM maven:3.9-eclipse-temurin-23 AS build
WORKDIR /PaymentGateway
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jdk-alpine
WORKDIR /PaymentGateway
COPY --from=build /PaymentGateway/target/PaymentEngine-0.0.1-SNAPSHOT.jar paymentengine.jar
EXPOSE 8080
CMD ["java", "-jar", "paymentengine.jar"]