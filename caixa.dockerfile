FROM openjdk:8
COPY caixa/target/caixa-0.0.1-SNAPSHOT.jar /var/www/
EXPOSE 8080
WORKDIR /var/www/
ENTRYPOINT ["java", "-jar", "caixa-0.0.1-SNAPSHOT.jar"]
