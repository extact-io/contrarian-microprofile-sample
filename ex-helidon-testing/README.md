# (番外編) Helidon MP Testing with JUnit5を使ってみる

## contents 
|章|TestClass|
| ---------- | --- |
| インテグレーションテストの実施 |PersonServiceItTest|
| 依存先をスタブに切り替える |PersonServiceStubTest|
| 設定を一時的に追加上書きする |PersonServiceStubWithConfigTest|
| メソッドごとにCDIコンテナを起動する |PersonServiceItAllTest|


## ビルドと実行
サンプルアプリのビルドにはJava11以上とMavenが必要です

```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
# Go into the sample
cd ex-helidon-testing
# build application
mvn clean verify
```

