FROM gradle:8.14.3-jdk21

WORKDIR /tmp
ADD . /tmp

RUN gradle build

CMD ["gradle", "clean", "bootRun"]
EXPOSE 9090
EXPOSE 9091
