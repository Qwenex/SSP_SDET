FROM maven:3.9.12-eclipse-temurin-17 AS build
COPY main/src /app/src
COPY main/pom.xml /app