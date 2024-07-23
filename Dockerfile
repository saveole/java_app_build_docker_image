FROM registry.cn-hangzhou.aliyuncs.com/szgs/nginx as final
LABEL maintainer="shengbox@gmail.com"
ENV TZ=Asia/Shanghai
COPY default.conf /etc/nginx/conf.d/
COPY dist /usr/share/nginx/html/
EXPOSE 80