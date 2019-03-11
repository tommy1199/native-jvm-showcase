FROM oracle/graalvm-ce:1.0.0-rc13 as builder
COPY build/libs/*-all.jar micronaut-demo.jar
ADD . build
RUN native-image --no-server \
             --class-path micronaut-demo.jar \
             -H:ReflectionConfigurationFiles=build/reflect.json \
             -H:DynamicProxyConfigurationFiles=build/proxy.json \
             -H:IncludeResources="logback.xml|application.yml|META-INF/services/*.*" \
             -H:Name=micronaut-demo \
             -H:Class=micronaut.demo.Application \
             -H:+ReportUnsupportedElementsAtRuntime \
             -H:+AllowVMInspection \
             -H:+ReportExceptionStackTraces \
             -H:-UseServiceLoaderFeature \
             --enable-http \
             --allow-incomplete-classpath \
             --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder,javax.net.ssl.SSLContext' \
             --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder,io.netty.handler.ssl.util.ThreadLocalInsecureRandom,com.sun.jndi.dns.DnsClient,io.netty.handler.ssl.util.BouncyCastleSelfSignedCertGenerator,io.netty.handler.ssl.JdkNpnApplicationProtocolNegotiator,io.netty.handler.ssl.ReferenceCountedOpenSslEngine,io.netty.handler.ssl.ReferenceCountedOpenSslClientContext,io.netty.handler.ssl.ReferenceCountedOpenSslServerContext,io.netty.handler.ssl.ConscryptAlpnSslEngine,io.netty.handler.ssl.JettyNpnSslEngine,org.postgresql.sspi.SSPIClient

FROM oraclelinux:7-slim AS base

FROM scratch

EXPOSE 8080

COPY --from=base /lib64/ld-linux-x86-64.so.2 /lib64/ld-linux-x86-64.so.2
COPY --from=base /lib64/libc.so.6 /lib64/libc.so.6
COPY --from=base /lib64/libcrypt.so.1 /lib64/libcrypt.so.1
COPY --from=base /lib64/libdl.so.2 /lib64/libdl.so.2
COPY --from=base /lib64/libfreebl3.so /lib64/libfreebl3.so
COPY --from=base /lib64/libnss_dns.so.2 /lib64/libnss_dns.so.2
COPY --from=base /lib64/libnss_files.so.2 /lib64/libnss_files.so.2
COPY --from=base /lib64/libpthread.so.0 /lib64/libpthread.so.0
COPY --from=base /lib64/libresolv.so.2 /lib64/libresolv.so.2
COPY --from=base /lib64/librt.so.1 /lib64/librt.so.1
COPY --from=base /lib64/libz.so.1 /lib64/libz.so.1

COPY --from=builder /micronaut-demo .

ENTRYPOINT ["./micronaut-demo"]