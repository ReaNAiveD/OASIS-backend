FROM alpine:latest
RUN apk --update add openjdk8-jre
CMD ["/usr/bin/java", "-version"]
COPY /target/oasis-0.0.1-SNAPSHOT.jar   /
CMD /usr/bin/java -jar /oasis-0.0.1-SNAPSHOT.jar