#сборка в докере требует mvnw, долгая, не всегда надежная (тянет зависимости).
#добавить mvnw можно так: mvn -N io.takari:maven:wrapper
#так что этот блок можно просто выкинуть и собирать локально
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw mvnw
RUN ./mvnw clean package -DskipTests=true

FROM tomcat:10.1.36-jre21-temurin
#копируем из образа выше итог сборки (можно удалить если надо быстро)
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
#и тогда собираем так, если сборка проведена локально (не забываем add *.war в git)
#COPY ./target/*.war /usr/local/tomcat/webapps/ROOT.war
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport"
EXPOSE 8080