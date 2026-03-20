# POS Online FastFlow — imagen para registry y despliegue
# Base: JRE 11 Alpine (ligera). Jenkins copia el JAR a target/app.jar antes de docker build.
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
# El stage "Build image" del Jenkinsfile hace: cp target/pos-online-*.jar target/app.jar
COPY target/app.jar app.jar
EXPOSE 8111
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8111"]
