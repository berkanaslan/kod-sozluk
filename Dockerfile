FROM openjdk:11-jre-slim
ADD target/eksi-sozluk-clone.jar eksi-sozluk-clone.jar
EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","/eksi-sozluk-clone.jar"]

