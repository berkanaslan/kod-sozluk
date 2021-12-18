FROM openjdk:11-jre-slim
ADD target/kod-sozluk.jar kod-sozluk.jar
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","/kod-sozluk.jar"]

