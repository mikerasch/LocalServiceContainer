FROM openjdk:23-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9095
ENTRYPOINT ["java","-jar","/app.jar"]