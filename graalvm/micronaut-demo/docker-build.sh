./gradlew assemble
docker build . -t micronaut-demo
docker run --network host micronaut-demo
