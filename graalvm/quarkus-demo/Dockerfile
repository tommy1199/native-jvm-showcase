FROM oracle/graalvm-ce:1.0.0-rc13 as builder

ENV GRAALVM_HOME /opt/graalvm-ce-1.0.0-rc13

ADD . app

WORKDIR /app

RUN ./gradlew quarkusNative --docker-build=true --stacktrace

RUN ls -l

