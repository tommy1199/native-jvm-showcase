FROM oracle/graalvm-ce:1.0.0-rc13 as builder

ENV GRAALVM_HOME /opt/graalvm-ce-1.0.0-rc13

ADD . app

WORKDIR /app

RUN ./mvnw -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN -Dorg.slf4j.simpleLogger.showDateTime=true -Djava.awt.headless=true package -Pnative

FROM frolvlad/alpine-glibc

COPY --from=builder /app/target/quarkus-demo-0.0.1-runner /app

CMD ["/app"]

