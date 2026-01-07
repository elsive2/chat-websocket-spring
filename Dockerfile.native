FROM ghcr.io/graalvm/native-image-community:25 AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew nativeCompile \
    --no-daemon \
    -Dspring.aot.enabled=true \
    -Dspring.context.cache.enabled=false

FROM scratch

WORKDIR /app

COPY --from=build /app/build/native/nativeCompile/chat-websocket /app/chat-websocket

EXPOSE 8080

ENTRYPOINT ["/app/chat-websocket"]