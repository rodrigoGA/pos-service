FROM openjdk:11-jdk
# ruta al jar
ARG JAR_FILE=target/pos-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=*:8000,server=y,suspend=n","-jar","/app.jar"]
