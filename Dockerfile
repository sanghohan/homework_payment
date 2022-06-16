FROM azul/zulu-openjdk-alpine:17

CMD ["./mvn", "clean", "package"]

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]