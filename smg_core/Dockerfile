FROM openjdk:8-alpine

ENV SRC_FOLDER=/usr/smg_core/app
ENV LOG_PATH=/var/log

WORKDIR $SRC_FOLDER
RUN adduser --uid 5151 --home $SRC_FOLDER --disabled-password smgcore

RUN mkdir -p $LOG_PATH
RUN chown -R smgcore:smgcore $LOG_PATH

USER smgcore
COPY target/*.jar ./smg_core.jar

ENTRYPOINT ["java", "-Xmx2g", "-jar", "smg_core.jar"]
