FROM openjdk:17-jdk-alpine
#ADD build/libs/*.jar app.jar
ADD build/libs/ms-devsecops-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]