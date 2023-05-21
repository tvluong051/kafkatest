FROM openjdk:8-alpine

ENV SRC_FOLDER=/usr/dummy_consumer/app
ENV LOG_PATH=/var/log

WORKDIR $SRC_FOLDER
RUN adduser --uid 5151 --home $SRC_FOLDER --disabled-password dummyconsumer

RUN mkdir -p $LOG_PATH
RUN chown -R dummyconsumer:dummyconsumer $LOG_PATH

USER dummyconsumer
COPY target/*.jar ./dummy_consumer.jar

ENTRYPOINT ["java", "-Xmx2g", "-jar", "dummy_consumer.jar"]
