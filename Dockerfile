# Build stage
FROM gradle:latest AS BUILD
WORKDIR /usr/app/
COPY . .
RUN gradle build

# Production stage
FROM openjdk:latest
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME/build/libs .
EXPOSE 8080
ENTRYPOINT exec java -jar $APP_HOME/reporting-service-0.0.1-SNAPSHOT.jar