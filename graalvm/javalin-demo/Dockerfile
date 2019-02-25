FROM oracle/graalvm-ce:1.0.0-rc12 as builder

COPY build/libs/*-all.jar javalin-demo.jar
COPY reflect.json reflect.json

RUN native-image --no-server \
             --class-path javalin-demo.jar \
             -H:EnableURLProtocols=http \
             -H:Name=javalin-demo \
             -H:Class=javalin.demo.Application \
             -H:+AllowVMInspection \
             -H:ReflectionConfigurationFiles=reflect.json \
             --static

FROM scratch

EXPOSE 8080

COPY --from=builder /javalin-demo .

ENTRYPOINT ["./javalin-demo"]