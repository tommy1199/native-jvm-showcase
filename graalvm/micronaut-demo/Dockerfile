FROM oracle/graalvm-ce:1.0.0-rc13 as builder
EXPOSE 8080
COPY build/libs/*-all.jar micronaut-demo.jar
ADD . build
RUN java -cp micronaut-demo.jar io.micronaut.graal.reflect.GraalClassLoadingAnalyzer 
RUN native-image --no-server \
             --class-path micronaut-demo.jar \
             -H:ReflectionConfigurationFiles=build/reflect.json \
             -H:EnableURLProtocols=http \
             -H:IncludeResources="logback.xml|application.yml|META-INF/services/*.*" \
             -H:Name=micronaut-demo \
             -H:Class=micronaut.demo.Application \
             -H:+ReportUnsupportedElementsAtRuntime \
             -H:+AllowVMInspection \
             -H:-UseServiceLoaderFeature \
             --static \
             --allow-incomplete-classpath \
             --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder,javax.net.ssl.SSLContext' \
             --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder,io.netty.handler.ssl.util.ThreadLocalInsecureRandom,com.sun.jndi.dns.DnsClient,io.netty.handler.ssl.util.BouncyCastleSelfSignedCertGenerator,io.netty.handler.ssl.JdkNpnApplicationProtocolNegotiator,io.netty.handler.ssl.ReferenceCountedOpenSslEngine

FROM scratch

EXPOSE 8080

COPY --from=builder /micronaut-demo .

ENTRYPOINT ["./micronaut-demo"]