FROM openjdk:20
COPY target/Accounting-0.0.1-SNAPSHOT.jar /app/Accounting-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/Accounting-0.0.1-SNAPSHOT.jar"]
