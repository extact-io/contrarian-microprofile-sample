# 第8回 MicroProfile Healthの機能と利用

## contents 
|章|MainClass|
| ---------- | --- |
| ヘルスチェックの実装 |OpenCloseStatusHealthCheck |
| 複数のヘルスチェック実装 |DbHealthCheck |


## ビルドと実行
サンプルアプリのビルドにはJava17以上とMavenが必要です

```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
# Go into the sample
cd 04-health
# build application
mvn clean package
# start application
java -jar target/health-sample.jar
```

