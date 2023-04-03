#
# Build stage
#
FROM maven:3.6.3-jdk-11-slim AS build
COPY ..
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build target/maven-wrapper.jar maven-wrapper.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","maven-wrapper.jar"]