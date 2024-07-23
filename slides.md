---
# try also 'default' to start simple
theme: dracula
# random image from a curated Unsplash collection by Anthony
# like them? see https://unsplash.com/collections/94734566/slidev
background: https://cover.sli.dev
# some information about your slides, markdown enabled
title: java_app_build_docker_image
info: |
  ## Slidev Starter Template
  Presentation slides for developers.

  Learn more at [Sli.dev](https://sli.dev)
# apply any unocss classes to the current slide
class: text-center
# https://sli.dev/custom/highlighters.html
highlighter: shiki
# https://sli.dev/guide/drawing
drawings:
  persist: false
# slide transition: https://sli.dev/guide/animations#slide-transitions
transition: slide-left
# enable MDC Syntax: https://sli.dev/guide/syntax#mdc-syntax
mdc: true
download: "https://little-ant.oss-cn-hangzhou.aliyuncs.com/pdf/how_to_build_java_app_docker_image.pdf"
exportFilename: how_to_build_java_app_docker_image.pdf
---

# Java 应用程序构建 Docker 镜像的几种方式

How to build Docker images for Java applications?

<div class="pt-12">
  <span @click="$slidev.nav.next" class="px-2 py-1 rounded cursor-pointer" hover="bg-white bg-opacity-10">
    Press Space for next page <carbon:arrow-right class="inline"/>
  </span>
</div>

<div class="abs-br m-6 flex gap-2">
  <!-- <button @click="$slidev.nav.openInEditor()" title="Open in Editor" class="text-xl slidev-icon-btn opacity-50 !border-none !hover:text-white">
    <carbon:edit />
  </button> -->
  <a href="https://github.com/saveole/saveole_slides/tree/main/java_app_build_docker_image" target="_blank" alt="GitHub" title="Open in GitHub"
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

<img src="https://dongshu.oss-cn-hangzhou.aliyuncs.com/scrm_front/0ccc911475124fe39df17a6fd97e5690docker-architecture.webp" />

<style>
img {
  width: 100%;
  height: 80%;
}
</style>

---

# Docker Client 的一些常用命令

|                                                                                                                                                           |          |
| --------------------------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| <kbd>pull</kbd> / <kbd>push</kbd> / <kbd>load</kbd> / <kbd>save</kbd>                                                                                     | 镜像相关 |
| <kbd>run</kbd> / <kbd>exec</kbd> / <kbd>cp</kbd> / <kbd>ps</kbd> / <kbd>stats</kbd> / <kbd>inspect</kbd> / <kbd>logs</kbd>                                | 容器相关 |
| <kbd>build</kbd> / <kbd>[镜像分层分阶段](https://docs.docker.com/build/guide/layers/)</kbd> / <kbd>[构建缓存](https://docs.docker.com/build/cache/)</kbd> | 构建相关 |

---

# [Dockerfile](https://docs.docker.com/reference/dockerfile/)

|                           |                |
| ------------------------- | -------------- |
| <kbd>FROM</kbd>           | 定义基础镜像   |
| <kbd>RUN</kbd>            | 在新层执行命令 |
| <kbd>WORKDIR</kbd>        | 工作目录       |
| <kbd>COPY/ADD</kbd>       | 拷贝文件       |
| <kbd>ARG</kbd>            | 构建时参数     |
| <kbd>ENV</kbd>            | 环境变量       |
| <kbd>EXPOSE</kbd>         | 暴露端口       |
| <kbd>CMD/ENTRYPOINT</kbd> | 运行容器的命令 |

---

# 镜像分层

<img src="https://dongshu.oss-cn-hangzhou.aliyuncs.com/scrm_front/ff3e6ed2a0124e35ae3f8000ce42774clayers.png" />

---

# Spring Boot Layers

```shell
# java -Djarmode=layertools -jar app.jar extract/list
- dependencies
- spring-boot-loader
- snapshot-dependencies
- application
```

<kbd>application/META-INF/MANIFEST.MF</kbd>

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

# [Dockerfile 最佳实践](https://docs.docker.com/build/building/best-practices/)

- **Use multi-stage builds** -> 构建+运行
- **Choose the right base image** -> 安全+轻量
- **Rebuild your images often** -> 更新依赖
- **Exclude with .dockerignore** -> 指定忽略文件
- **Don't install unnecessary packages** -> 减少体积
- **Sort multi-line arguments** -> 增加可读性
- **Leverage build cache** -> 灵活使用构建缓存
- **Pin base image versions** -> 镜像版本管理
- **Build and test your images in CI** -> 结合 CI/CD

---

# Java 对容器环境的支持

### 1. 为什么需要支持容器化？

- 云原生时代下的容器化趋势
- JVM 不能感知 <kbd>[cgoups](https://tech.meituan.com/2015/03/31/cgroups.html)</kbd>

### 2. [Java 8.0_131 之后对容器的支持](https://blogs.oracle.com/java/post/java-on-container-like-a-pro)

- <kbd>[-XX:+UseContainerSupport](https://chriswhocodes.com)</kbd>
- <kbd>[JVM default ergonomics](https://learn.microsoft.com/en-us/azure/developer/java/containers/overview)</kbd>

| 约束                              | GC 类型  |
| --------------------------------- | -------- |
| m <= 1791MB <kbd>+</kbd> 任意 cpu | SerialGC |
| m >= 1792MB <kbd>+</kbd> 2+ cpu   | G1GC     |

- <kbd>不同容器环境的 GC 选择参考如下：</kbd>

---

| Factors             | SerialGC | ParallelGC                                 | G1GC               | ZGC                | ShenandoahGC       |
| ------------------- | -------- | ------------------------------------------ | ------------------ | ------------------ | ------------------ |
| CPU 核数            | 1        | 2                                          | 2                  | 2                  | 2                  |
| 多线程              | No       | Yes                                        | Yes                | Yes                | Yes                |
| 堆内存              | < 4g     | < 4g                                       | > 4g               | > 4g               | > 4g               |
| 是否 STW            | Yes      | Yes                                        | Yes                | Yes(<1ms)          | Yes(<10ms)         |
| 开销                | 低       | 低                                         | 中                 | 中                 | 中                 |
| Tail-latency-Effect | 高       | 高                                         | 高                 | 低                 | 中                 |
| JDK 版本            | All      | All                                        | JDK 8+             | JDK 17+            | JDK 11+            |
| 适用场景            | 单核小堆 | 具有任何堆大小的多核小型堆或批处理工作负荷 | 延迟优先的中大型堆 | 延迟优先的中大型堆 | 延迟优先的中大型堆 |

---

# APM

- 主机监控 - Node Exporter + [Prometheus](http://192.168.3.9:9090) + [Grafana](http://192.168.3.9:3000)
- ~~容器监控 - Cadvisor~~
- 应用监控 - [Spring-Boot-Admin](http://192.168.3.9:8080/applications)
- 方法监控 - [JFR](https://openjdk.org/jeps/328) + [OpenTelemetry](https://spring.io/blog/2022/10/12/observability-with-spring-boot-3)

---

# Trouble Shooting

- GC 日志
  - [gceasy.io](https://www.gceasy.io/)
  - [jifa](http://192.168.3.9:8102)
- Heap dump
  - mat
  - [jifa](http://192.168.3.9:8102)
- Thread dump
  - [jifa](http://192.168.3.9:8102)
  - jstack ${pid}
- Native memory leak
  - `-XX:NativeMemoryTracking=summary`

---

# Examples

---

# Summary

- Dockerfile

  - <kbd>FROM</kbd>
  - <kbd>COPY/ADD</kbd>
  - <kbd>RUN</kbd>
  - <kbd>ENV</kbd>
  - <kbd>EXPOSE</kbd>
  - <kbd>CMD/ENTRYPOINT</kbd>

- 构建 Java 应用程序镜像的几种方式
  - <kbd>Fat jar</kbd> + <kbd>Full JDK</kbd>
  - <kbd>Fat jar</kbd> + <kbd>Slim JDK</kbd>
  - <kbd>Spring boot layers</kbd>
  - <kbd>Spring boot native image</kbd>

---

# Resources

- ## 博客/周刊类
  - [Inside Java](https://inside.java/)
  - [Baeldung Weekly](https://www.baeldung.com/category/weekly-review)
  - [Red Hat Developer Blog](https://developers.redhat.com/blog)
  - [Oracle Blogs | Java](https://blogs.oracle.com/java/)
  - [Java Annotated](https://blog.jetbrains.com/idea/tag/java-annotated/)
- ## 官网类
  - [Docker](https://www.docker.com/)
  - [OpenJDK](https://openjdk.org/)
  - [Spring Boot](https://docs.spring.io/spring-boot/)
  - [Learn Java](https://dev.java/learn/)
- ## PPT 制作工具
  - [Slidev](https://cn.sli.dev/)

---

<h1> Thank You 🙏 </h1>

<style>
  body {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
  }
  h1 {
    text-align: center;
  }
</style>
