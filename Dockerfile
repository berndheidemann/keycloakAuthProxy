FROM adoptopenjdk:11-jre-openj9
MAINTAINER Bernd Heidean <b.heidemann@schule.bremen.de>

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ARG SPRING_BOOT_PROFILE

ENV WEB_IP "$WEB_ADDRESS_INT"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]