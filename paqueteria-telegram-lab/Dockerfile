FROM eclipse-temurin:21-jdk as build
WORKDIR /app
COPY . .
RUN ./mvnw -v >/dev/null 2>&1 || (apt-get update && apt-get install -y maven) && mvn -q -e -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
