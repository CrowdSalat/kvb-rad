FROM eclipse-temurin:17.0.4.1_1-jdk-jammy

RUN curl --create-dirs -o /home/kvbrad/.postgresql/root.crt -O https://cockroachlabs.cloud/clusters/39bf8cb2-2dfd-4865-99f5-42b6130303e1/cert && \
    groupadd -r kvbrad && useradd -r -g kvbrad kvbrad && \
    chown -R kvbrad:kvbrad /home/kvbrad/

USER kvbrad

ARG JAR_FILE=target/*.jar
COPY --chown=kvbrad:kvbrad ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
