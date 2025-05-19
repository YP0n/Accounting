# Етап збірки з Maven + JDK
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Копіюємо лише pom.xml для кешування залежностей
COPY pom.xml .

# Завантажуємо залежності (кешування)
RUN mvn dependency:go-offline

# Копіюємо весь код проекту
COPY src ./src

# Збираємо проект (пропускаємо тести)
RUN mvn clean package -DskipTests

# Етап запуску — легкий JDK образ
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Копіюємо з етапу build готовий jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "app.jar"]
