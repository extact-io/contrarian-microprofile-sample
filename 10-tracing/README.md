# 第16回 MicroProfile OpenTracingとJeagerで理解する分散トレーシング

## contents 
|サービス|フォルダ|
| ---------- | --- |
| HelloAggregateサービス |[hello-aggregator](hello-aggregator-tr/) |
| Helloサービス |[hello-service](hello-service-tr/) |
| RandomHelloサービス |[hello-service](hello-service-random-tr/) |


## ビルドと実行
サンプルアプリのビルドにはJava17以上とMavenが必要です

### リポジトリclone
```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
```

### HelloAggregateサービスのビルドと実行
```shell
# Go into HelloAggrega Service
cd contrarian-microprofile-sample/10-tracing/hello-aggregator-tr/
# build application
mvn clean package
# start application
java -jar target/hello-aggregator-tr.jar &
```
### Helloサービスのビルドと実行
```shell
# Go into Hello Service
cd contrarian-microprofile-sample/10-tracing/hello-service-tr/
# build application
mvn clean package
# start application
java -jar -Dserver.port=7002 -Dhello.message=こんにちは target/hello-service-tr.jar &
java -jar -Dserver.port=7003 -Dhello.message=Hello target/hello-service-tr.jar &
java -jar -Dserver.port=7004 -Dhello.message=ニイハオ target/hello-service-tr.jar &
```
### RandomHelloサービスのビルドと実行
```shell
# Go into Hello Service
cd contrarian-microprofile-sample/10-tracing/hello-service-random-tr/
# build application
mvn clean package
# start application
export OTEL_TRACES_EXPORTER=otlp
export OTEL_PROPAGATORS=b3
export OTEL_METRICS_EXPORTER=none
export OTEL_SERVICE_NAME=RandomHelloService
java -javaagent:./opentelemetry-javaagent.jar -jar target/hello-service-random-tr.jar &
```

### Jeager All-in-oneコンテナの起動
```shell
docker run -d --name jaeger \
  -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 4317:4317 \
  -p 4318:4318 \
  -p 14250:14250 \
  -p 14268:14268 \
  -p 14269:14269 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.39
```

### HelloAggregateサービスの呼び出し
```shell
# call by sync
curl localhost:7001/aggregate/sync
```
