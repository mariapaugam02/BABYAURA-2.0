FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY dist/monitoreo_temp.jar app.jar
COPY dist/lib lib

EXPOSE 8080

CMD ["java", "-cp", "app.jar:lib/*", "monitoreo_temp.Monitoreo_temp"]
