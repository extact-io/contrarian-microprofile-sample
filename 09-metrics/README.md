# 第15回 MicroProfile Metricsの機能と利用

## contents 
|章|参照コード|
| ---------- | --- |
| メトリクスの種類 |[HelloRandomResource](src/main/java/io/extact/mp/sample/metrics/HelloRandomResource.java)|
| メトリクスの指定(プログラミングモデル) |[HelloRandomResourceByAnnotation](src/main/java/io/extact/mp/sample/metrics/HelloRandomResourceByAnnotation.java)|
| |[HelloRandomResourceByResistry](src/main/java/io/extact/mp/sample/metrics/HelloRandomResourceByResistry.java)|


## ビルドと実行
サンプルアプリのビルドにはJava17以上とMavenが必要です

### リポジトリclone
```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
```

### サンプルアプリのビルドと実行
```shell
# Go into sample application
cd contrarian-microprofile-sample/09-metrics/
# build application
mvn clean package
# start application
java -jar target/hello-random.jar
```

### HelloRandomサービスの呼び出しとメトリクスの取得
```shell
# request to HelloRandom Service
curl localhost:7001/hello/
Hello
...
# request to MicroProfileMetrics
curl -H 'Accept:application/json' localhost:7001/metrics/application/helloCallCounter
{"helloCallCounter":1}
...
```
