FROM openjdk:17-jdk-alpine
#ADD build/libs/*.jar app.jar
ADD build/libs/HCL DevSecOps-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]