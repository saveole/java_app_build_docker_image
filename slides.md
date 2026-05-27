---
# try also 'default' to start simple
theme: academic
layout: cover
coverAuthor: saveole
coverAuthorUrl: https://github.com/saveole
coverBackgroundUrl: /cover.jpg
# coverBackgroundSource: unsplash
coverBackgroundSourceUrl: https://images.unsplash.com/photo-1721736134606-984c6c961c04?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D
# some information about your slides, markdown enabled
title: java_app_build_docker_image
info: |
  ## Slidev Starter Template
  Presentation slides for developers.

  Learn more at [Sli.dev](https://sli.dev)
# apply any unocss classes to the current slide
class: text-white
# https://sli.dev/custom/highlighters.html
highlighter: shiki
# https://sli.dev/guide/drawing
drawings:
  persist: false
# slide transition: https://sli.dev/guide/animations#slide-transitions
transition: fade-out
# enable MDC Syntax: https://sli.dev/guide/syntax#mdc-syntax
mdc: true
download: "https://little-ant.oss-cn-hangzhou.aliyuncs.com/pdf/how_to_build_java_app_docker_image.pdf"
exportFilename: how_to_build_java_app_docker_image.pdf
---

# Java 应用构建 Docker 镜像

How to build Docker images for Java applications?

<div class="abs-br m-6 flex gap-2">
  <a href="https://github.com/saveole/java_app_build_docker_image" target="_blank" alt="GitHub" title="Open in GitHub"
    class="text-xl slidev-icon-btn opacity-50 !border-none !hover:text-white">
    <carbon-logo-github />
  </a>
</div>

<!--
The last comment block of each slide will be treated as slide notes. It will be visible and editable in Presenter Mode along with the slide. [Read more in the docs](https://sli.dev/guide/syntax.html#notes)
-->

---

# Topics

- 🐳 **Docker** - 容器引擎
- 📄 **Dockerfile** - 镜像构建文件
- 🚀 **Container and Java** - Java 对容器化的支持
- 🛠 **APM and Trouble Shooting** - 如何监控和问题排查
- 👉 **Examples** - 示例
- 📚 **Summary && Resources** - 总结及资源分享

<!--
Here is another comment.
-->

---

# Docker

Docker is an open platform for developing, shipping, and running applications. Docker enables you to separate your applications from your infrastructure so you can deliver software quickly. [learn more](https://docs.docker.com/guides/docker-overview/)

<img src="/docker-architecture.webp" class="m-0 h-90 rounded shadow" />

---

# Docker Client 的一些常用命令
## 1.镜像相关
<v-clicks>

- <Tooltip tip="拉取镜像以及被墙了怎么办？" content="pull" />
- <Tooltip tip="将镜像推到仓库" content="push" />
- <Tooltip tip="docker load < xxx.tar" content="load" />
- <Tooltip tip="docker save [id/tag] > xxx.tar" content="save" />
- <Tooltip tip="docker image history [id.tag]" content="history" />
- <Tooltip tip="docker image prune -a" content="image/s" />
- <Tooltip tip="删除镜像" content="rmi" />

</v-clicks>

---

# Docker Client 的一些常用命令
## 2.容器相关
<v-clicks>

- <Tooltip tip="docker run --name xx -p 18080:8080 -v /path:/var/xx [image]" content="run" />
- <Tooltip tip="docker exec -it [容器] sh" content="exec" />
- <Tooltip tip="docker cp [容器]:/xx文件 xx文件" content="cp" />
- <Tooltip tip="docker ps [-a]" content="ps" />
- <Tooltip tip="docker stats" content="stats" />
- <Tooltip tip="查看容器基本信息" content="inspect" />
- <Tooltip tip="docker logs -f [容器]" content="logs" />
- <Tooltip tip="删除停止运行的容器" content="rm" />
- <Tooltip tip="运行/停止容器" content="start/stop" />

</v-clicks>

---


# Docker Client 的一些常用命令
## 3.构建相关
<v-clicks>

- <Tooltip tip="docker build -f Dockerfile -t [artifactId]:[version] ." content="build" />
- <kbd>[镜像分层分阶段](https://docs.docker.com/build/guide/layers/)</kbd> 
- <kbd>[构建缓存](https://docs.docker.com/build/cache/)</kbd>

</v-clicks>

---
layout: two-cols-header
---

## [Dockerfile](https://docs.docker.com/reference/dockerfile/)

::left::

<div class="text-sm pr-10 border-1 border-gray-200">

|                           |                |
| ------------------------- | -------------- |
| <kbd>FROM</kbd>           | 定义基础镜像   |
| <kbd>RUN</kbd>            | 在新层执行命令 |
| <kbd>[WORKDIR](https://yeasy.gitbook.io/docker_practice/image/dockerfile/workdir)</kbd>        | 工作目录       |
| <kbd>[COPY](https://yeasy.gitbook.io/docker_practice/image/dockerfile/copy)/[ADD](https://yeasy.gitbook.io/docker_practice/image/dockerfile/add)</kbd>       | 拷贝文件       |
| <kbd>[ARG](https://yeasy.gitbook.io/docker_practice/image/dockerfile/arg)</kbd>            | 构建时参数     |
| <kbd>[ENV](https://yeasy.gitbook.io/docker_practice/image/dockerfile/env)</kbd>            | 环境变量       |
| <kbd>[EXPOSE](https://yeasy.gitbook.io/docker_practice/image/dockerfile/expose)</kbd>         | 暴露端口       |
| <kbd>[CMD](https://yeasy.gitbook.io/docker_practice/image/dockerfile/cmd)/[ENTRYPOINT](https://yeasy.gitbook.io/docker_practice/image/dockerfile/entrypoint)</kbd> | 运行容器的命令  |
| <kbd>[HEALTHCHECK](https://yeasy.gitbook.io/docker_practice/image/dockerfile/healthcheck)</kbd>    | [健康检查](https://docs.docker.com/reference/dockerfile/#healthcheck)       |
| <kbd>[VOLUME](https://yeasy.gitbook.io/docker_practice/image/dockerfile/volume)</kbd>          | 数据卷           |

</div>

::right::

<div class="text-sm pl-5">

```docker
# From base image
FROM dockerproxy.cn/eclipse-temurin:21 AS jre-build

# Run some command to create a custom Java runtime
RUN $JAVA_HOME/bin/jlink \
         --add-modules java.base \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime

# Define your base image as runtime base
FROM dockerproxy.cn/debian:bookworm-slim

# Setup JAVA_HOME
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /javaruntime $JAVA_HOME

WORKDIR /
COPY HelloWorld.class /
ENTRYPOINT ["java", "--enable-preview", \
        "-XshowSettings:system", "-Xlog:gc=info", \
        "HelloWorld"]
```

</div>

---

# 镜像分层

<img src="/layers.png" class="m-0 h-90 rounded shadow" />

---

# Spring Boot Layers

```shell
# java -Djarmode=layertools -jar app.jar extract
- dependencies
- spring-boot-loader
- snapshot-dependencies
- application
```

### <Tooltip tip="当前云效流水线构建集群并不适用" content="application/META-INF/MANIFEST.MF" />

```shell
Manifest-Version: 1.0
Created-By: Maven JAR Plugin 3.4.1
Build-Jdk-Spec: 21
Implementation-Title: chat
Implementation-Version: 0.0.1-SNAPSHOT
Spring-Boot-Native-Processed: true
Main-Class: org.springframework.boot.loader.launch.JarLauncher
Start-Class: com.ds.chat.ChatApplication
Spring-Boot-Version: 3.3.0
Spring-Boot-Classes: BOOT-INF/classes/
Spring-Boot-Lib: BOOT-INF/lib/
Spring-Boot-Classpath-Index: BOOT-INF/classpath.idx
Spring-Boot-Layers-Index: BOOT-INF/layers.idx
```

---

# Spring Boot CDS

- Java CDS -> [Class Data Sharing](https://docs.oracle.com/en/java/javase/21/vm/class-data-sharing.html)
- [Spring + CDS](https://docs.spring.io/spring-framework/reference/integration/cds.html#_creating_the_cds_archive)
- Steps
  1. 生成 CDS 数据: `java -Dspring.aot.enabled=true -Dspring.context.exit=onRefresh -XX:ArchiveClassesAtExit=app.jsa -jar app/app.jar`
  2. 启动时加载 CDS 数据: `java -XX:SharedArchiveFile=app.jsa -jar app.jar`

- [Spring AOT](https://docs.spring.io/spring-framework/reference/core/aot.html)
---

# [Dockerfile 最佳实践](https://docs.docker.com/build/building/best-practices/)

- **Use multi-stage builds** -> 构建+运行
- **Choose the right base image** -> 安全+轻量
- **Rebuild your images often** -> 更新依赖
- **Exclude with .dockerignore** -> 指定忽略文件
- **Don't install unnecessary packages** -> 减少体积
- **Minimal number of layers** -> 减少分层数
- **Sort multi-line arguments** -> 增加可读性
- **Leverage build cache** -> 灵活使用构建缓存
- **Offen changed goes last** -> 频繁变动的部分放最后
- **Pin base image versions** -> 镜像版本管理
- **Build and test your images in CI** -> 结合 CI/CD

---

# Java 对容器环境的支持

### 1. 为什么需要支持容器化？

- 云原生时代下的容器化趋势
- JVM 不能感知 <kbd>[cgroups](https://tech.meituan.com/2015/03/31/cgroups.html)</kbd>

<v-click>

<br/>

### 2. [since JDK10,8u191 对容器的支持](https://blogs.oracle.com/java/post/java-on-container-like-a-pro)

- <kbd>[-XX:+UseContainerSupport](https://chriswhocodes.com)</kbd>
- <kbd>[JVM default ergonomics](https://learn.microsoft.com/en-us/azure/developer/java/containers/overview)</kbd>

| 约束                              | GC 类型  |
| --------------------------------- | -------- |
| m <= 1791MB <kbd>+</kbd> 任意 cpu | SerialGC |
| m >= 1792MB <kbd>+</kbd> 2+ cpu   | G1GC     |

</v-click>

---

# 不同容器环境的 GC 选择参考如下：

<img src="/Determine-GC.png" class="m-0 h-100 rounded shadow" />

---
layout: 2x2-grid-header
---

# APM


::topleft::

<v-click>

### 1. 主机监控 - [Node Exporter](http://192.168.3.9:3000)

<img src="/node-exporter.png" class="m-2 h-40 rounded shadow" />

</v-click>

::topright::

<v-click>

### 2. 容器监控 - [Cadvisor](http://192.168.3.9:8090)

<img src="/stats.png" class="m-2 h-40 rounded shadow" />

</v-click>

::bottomleft::

<v-click>

### 3. 应用监控 - [Spring-Boot-Admin](http://192.168.3.9:8080/applications)

<img src="/spring-boot-admin.png" class="m-2 h-40 rounded shadow" />

</v-click>

::bottomright::

<v-click>

### 4. 方法监控 - [JFR](https://openjdk.org/jeps/328) + [OpenTelemetry](https://spring.io/blog/2022/10/12/observability-with-spring-boot-3)

<img src="/flame-graph.png" class="m-2 h-40 rounded shadow" />

</v-click>


---
layout: 2x2-grid-header
---

# Trouble Shooting

::topleft::

<v-click>

### GC 日志

- [gceasy.io](https://www.gceasy.io/)
- [jifa](http://192.168.3.9:8102)
</v-click>

::topright::

<v-click>

### Heap dump

- mat
- [jifa](http://192.168.3.9:8102)

</v-click>

::bottomleft::

<v-click>

### Thread dump

- [jifa](http://192.168.3.9:8102)
- jstack ${pid}

</v-click>

::bottomright::
<v-click>

### Native memory leak

- `-XX:NativeMemoryTracking=[summary|detail]`
- `jcmd PID VM.native_memory [detail]`

</v-click>

---

# Examples

---

layout: two-cols-header
---

## Example 1: 简单 Java 应用

::left::

### Dockerfile (JRE Alpine)

```dockerfile
FROM eclipse-temurin:21-jre-alpine
WORKDIR /
COPY HelloWorld.class /
CMD ["java", "--enable-preview",
     "-XshowSettings:system",
     "-Xlog:gc=info", "HelloWorld"]
```

::right::

### Dockerfile (jlink 多阶段构建)

```dockerfile
FROM eclipse-temurin:21 AS jre-build
RUN $JAVA_HOME/bin/jlink \
    --add-modules java.base,java.management,jdk.management \
    --strip-debug --no-man-pages --no-header-files \
    --compress=2 --output /javaruntime

FROM debian:bookworm-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /javaruntime $JAVA_HOME
WORKDIR /
COPY HelloWorld.class /
ENTRYPOINT ["java", "--enable-preview",
    "-XshowSettings:system", "HelloWorld"]
```

---

layout: two-cols-header
---

## Example 2: APM 监控栈 (docker-compose)

::left::

```yaml
services:
  prometheus:
    image: prom/prometheus:latest
    volumes:
      - ./prometheus/prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - 9090:9090

  grafana:
    image: grafana/grafana:latest
    volumes:
      - ./grafana/:/etc/grafana/provisioning/
    ports:
      - 3000:3000
    depends_on:
      - prometheus

  node-exporter:
    image: prom/node-exporter:latest
    volumes:
      - /proc:/host/proc:ro
      - /sys:/host/sys:ro
    ports:
      - 9100:9100
```

::right::

### 完整示例

- Node Exporter -> 主机指标采集
- Prometheus -> 指标存储与查询
- Grafana -> 可视化仪表盘
- Spring Boot Admin -> 应用监控
- Docker Container Stats -> 容器资源统计

> 完整配置见 `samples/apm/docker-compose.yml`

---
layout: 2x2-grid-header
---

# Summary

::topleft::

<v-click>

### 1. Dockerfile

- <kbd>FROM</kbd>
- <kbd>COPY/ADD</kbd>
- <kbd>RUN</kbd>
- <kbd>ENV/EXPOSE</kbd>
- <kbd>CMD/ENTRYPOINT</kbd>

</v-click>

::topright::
<v-click>

### 2. 镜像构建通用原则

- <kbd>轻量化</kbd>
- <kbd>安全性</kbd>
- <kbd>镜像分层</kbd>
- <kbd>Dockerfile 可读性</kbd>

</v-click>

::bottomleft::
<v-click>

### 3. 普通 Java 应用镜像构建

- <kbd>模块化</kbd>
- <kbd>jdeps</kbd>
- <kbd>jlink</kbd>
- <kbd>native</kbd>

</v-click>

::bottomright::
<v-click>

### 4. Spring Boot 应用镜像构建

- <kbd>Fat jar</kbd> + <kbd>Full JDK</kbd>
- <kbd>Fat jar</kbd> + <kbd>Slim JDK</kbd>
- <kbd>Spring boot layers</kbd>
- <kbd>Spring boot native image</kbd>

</v-click>
  
---
layout: 2x2-grid-header
---

# Resources

::topleft::

### 博客/周刊

- [Inside Java](https://inside.java/)
- [Baeldung Weekly](https://www.baeldung.com/category/weekly-review)
- [Red Hat Developer Blog](https://developers.redhat.com/blog)
- [Oracle Blogs | Java](https://blogs.oracle.com/java/)
- [Java Annotated](https://blog.jetbrains.com/idea/tag/java-annotated/)

::topright::

### 官网

- [Docker](https://www.docker.com/)
- [OpenJDK](https://openjdk.org/)
- [Spring Boot](https://docs.spring.io/spring-boot/)
- [Learn Java](https://dev.java/learn/)

::bottomleft::

### 视频

- [Memory footprint of a Java process by Andrei Pangin](https://www.bilibili.com/video/BV14hemeoEkp)
- [Fast and Lightweight Spring Boot Applications with GraalVM](https://www.bilibili.com/video/BV1Ju41147Nu)
- [A Glance at The Java PerformanceToolbox](https://www.bilibili.com/video/BV1ga4y1u7HV)

::bottomright::

### PPT 制作工具

- [Slidev](https://cn.sli.dev/)

---
layout: center
class: "text-center"
---

# Thanks for Listening!

<br />
