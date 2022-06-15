FROM azul/zulu-openjdk-alpine:17

CMD ["./mvn", "clean", "package"]

WORKDIR target

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "homework_payment.jar"]