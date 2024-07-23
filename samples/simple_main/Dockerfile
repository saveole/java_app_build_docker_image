FROM dockerproxy.cn/eclipse-temurin:21-jre-alpine
WORKDIR /
COPY HelloWorld.class /
CMD ["java", "--enable-preview", "-XshowSettings:system", "-Xlog:gc=info",  "HelloWorld"]
