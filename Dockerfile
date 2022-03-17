# usage example - must define VERSION variable
# prerequisits:
#           gradle build
# build (use correct version):
#           docker build -t kafka-producer-app:0.0.1 --build-arg VERSION=0.0.1 .
# run:
#           sudo docker run --name kafka-producer -d -p 9090:8080 kafka-producer-app:0.0.1

FROM openjdk:11
ARG VERSION
ARG JAR_FILE=build/libs/kafka-producer-app-${VERSION}.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]