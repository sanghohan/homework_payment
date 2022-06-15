FROM azul/zulu-openjdk-alpine:17

CMD ["./mvnw", "clean", "package"]

RUN cp ./target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]