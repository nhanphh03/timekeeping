FROM quay-dev.lottefn.vn/rbp/openjdk:17-alpine

### Set environtment
ENV SPRING_PROFILES_ACTIVE=dev
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:MaxPermSize=256m"
WORKDIR /opt/app

### Create folder
RUN mkdir -p /opt/app/log
RUN mkdir -p /opt/app/config

### Copy file
COPY target/*.jar /opt/app/collateral-api.jar

### Pushlish port
EXPOSE 8082

### Run service
ENTRYPOINT ["java", "-Dnet.bytebuddy.experimental=true", "-Dspring.config.location=file:/opt/app/config/", "-jar", "collateral-api.jar" ]
