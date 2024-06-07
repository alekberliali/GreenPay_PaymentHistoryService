FROM gradle:7.5.1-jdk17 AS build

WORKDIR /app

COPY build.gradle ./
COPY settings.gradle ./
COPY ./src ./src

RUN gradle build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/paymentHistoryService.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/paymentHistoryService.jar"]
CMD [""]