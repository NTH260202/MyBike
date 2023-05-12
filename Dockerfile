FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/Bike-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
RUN echo $TEST
ENTRYPOINT ["java","-Duser.timezone=UTC","-jar","/app.jar"]