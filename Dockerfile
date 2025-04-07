# Используем официальный образ JDK 23 от Oracle для сборки
# Используем официальный образ Oracle Linux
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /app

# Копируем файлы проекта и скачиваем зависимости
COPY pom.xml ./
RUN mvn dependency:go-offline

# Копируем все файлы проекта и собираем приложение
COPY src ./src
RUN mvn clean package -DskipTests

# Используем JDK 17 для запуска приложения
FROM openjdk:23
WORKDIR /app

# Копируем сгенерированный jar файл из предыдущего stage
COPY --from=build /app/target/*.jar ./app.jar

# Указываем порт, на котором будет работать приложение
EXPOSE 8081

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]