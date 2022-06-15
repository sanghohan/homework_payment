FROM azul/zulu-openjdk-alpine:17

CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=target/*.jar

RUN cp ${JAR_FILE_PATH} app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]