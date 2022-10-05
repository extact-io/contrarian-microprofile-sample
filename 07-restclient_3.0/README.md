# 第11回 MicroProfile RestClient 3.0の確認と小技機能の紹介

## contents 
|サービス|フォルダ|
| ---------- | --- |
| HelloAggregateサービス |[hello-aggregator](hello-aggregator/) |
| Helloサービス |[hello-service](hello-service/) |


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
cd contrarian-microprofile-sample/07-restclient_3.0/hello-aggregator/
# build application
mvn clean package
# start application
java -jar target/hello-aggregator.jar &
```
### Helloサービスのビルドと実行
```shell
# Go into Hello Service
cd contrarian-microprofile-sample/07-restclient_3.0/hello-service/
# build application
mvn clean package
# start application
java -jar -Dserver.port=7002 -Dhello.message=こんにちは target/hello-service.jar &
java -jar -Dserver.port=7003 -Dhello.message=Hello target/hello-service.jar &
java -jar -Dserver.port=7004 -Dhello.message=ニイハオ target/hello-service.jar &
```

### HelloAggregateサービスの呼び出し
```shell
# call by sync
curl localhost:7001/aggregate/sync
# call by async
curl localhost:7001/aggregate/async
```
