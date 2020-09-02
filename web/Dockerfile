FROM openjdk:11-jdk-slim

RUN apt-get update && apt-get install -y netcat
RUN groupadd spring
RUN useradd spring -g spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

COPY entrypoint.sh /.
ENTRYPOINT ["bash",  "/entrypoint.sh"]
CMD ["java","-jar","/app.jar"]
