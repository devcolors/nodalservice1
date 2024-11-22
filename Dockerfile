FROM openjdk:8-jdk-alpine
COPY target/nodalService1-1.2-SNAPSHOT-jar-with-dependencies.jar nodalService1.jar
ENTRYPOINT ["java", "-jar", "/nodalService1.jar"]
