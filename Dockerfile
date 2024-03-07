# Використовуємо офіційний образ OpenJDK для Java 20
FROM openjdk:20

# Встановлюємо робочу директорію
WORKDIR /app

# Копіюємо JAR файл вашого Spring проєкту до контейнера
COPY target/Accounting-0.0.1-SNAPSHOT.jar /app/Accounting-0.0.1-SNAPSHOT.jar

# Виконуємо команду для запуску додатку Spring при запуску контейнера
CMD ["java", "-jar", "/app/Accounting-0.0.1-SNAPSHOT.jar"]
