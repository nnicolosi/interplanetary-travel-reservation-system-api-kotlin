FROM openjdk:11

COPY ./build/libs/reservationsystem-0.0.1-SNAPSHOT.jar ./interplanetary.jar

CMD java -jar ./interplanetary.jar
