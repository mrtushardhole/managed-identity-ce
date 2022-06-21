FROM gradle:7-jdk11-hotspot AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle wrapper
RUN ./gradlew clean build

FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.11_9_openj9-0.26.0-alpine-slim
COPY --from=builder /home/gradle/src/build/libs/podidentitydemoapp-*-all.jar podidentitydemoapp.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-Dlog4j2.formatMsgNoLookups=true", "-jar", "podidentitydemoapp.jar"]
