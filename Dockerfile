FROM 3.9.9-eclipse-temurin-23 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:23
WORKDIR /PayoutEngine
COPY ./target/PayoutEngine-0.0.1-SNAPSHOT.jar /PayoutEngine
EXPOSE 8080
CMD ["java", "-jar", "PayoutEngine-0.0.1-SNAPSHOT.jar"]