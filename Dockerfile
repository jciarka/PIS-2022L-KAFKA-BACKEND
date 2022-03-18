# usage example - must define VERSION variable
# prerequisits:
#           gradle build
# build (use correct version):
#           docker build -t pis-kafka-prod:0.0.1 --build-arg VERSION=0.0.1 .
# run:
#           sudo docker run --name kafka-prod -d -p 9090:9090 pis-kafka-prod:0.0.1

#push:
#           sudo docker tag pis-kafka-prod:0.0.1 jciarka/pis-kafka-prod:0.0.1
#           sudo docker push jciarka/pis-kafka-prod:0.0.1

# deploy:
#           kubectl apply -f pis-kafka-prod-depl.yaml

FROM openjdk:11
ARG VERSION
ARG JAR_FILE=build/libs/kafka-producer-app-${VERSION}.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]