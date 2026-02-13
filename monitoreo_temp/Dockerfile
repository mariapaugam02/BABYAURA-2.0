FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY dist/monitoreo_temp-all.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
