FROM openjdk:8-jdk-alpine
COPY ./target/demo-catalog-0.0.1-SNAPSHOT.jar /catalog.jar
ENTRYPOINT ["java","-jar","/catalog.jar"]