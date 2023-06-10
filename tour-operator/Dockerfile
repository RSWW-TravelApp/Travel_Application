FROM ubuntu as packages
RUN apt-get update && apt-get install -y iputils-ping && apt-get install -y curl

FROM eclipse-temurin:17.0.4.1_1-jre-alpine as builder
WORKDIR extracted
ADD target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.4.1_1-jre-alpine
COPY --from=packages /bin/ping /bin/ping
COPY --from=packages /bin/curl /bin/curl
WORKDIR application
ADD src/main/java/touroperator/flights_ids.txt flights_ids.txt
ADD src/main/java/touroperator/offers_ids.txt offers_ids.txt
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
