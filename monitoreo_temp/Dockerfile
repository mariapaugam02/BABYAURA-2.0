FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY dist/monitoreo_temp.jar app.jar

EXPOSE 8080

CMD ["java", "-cp", "monitoreo_temp.jar:lib/*", "monitoreo_temp.Monitoreo_temp"]
