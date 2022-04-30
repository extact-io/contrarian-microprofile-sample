# (番外編) MicroProfile RestClientを使ったRESTリースのJUnitテスト

## contents 
この記事のサンプルサプリは「第7回 らくらくMicroProfile RestClient」のサンプル資材([/01-restclient/server](/01-restclient/server/))に含まれています。

- [PersonResourceTest](/01-restclient/server/src/test/java/io/extact/mp/sample/restclient/resource/PersonResourceTest.java)

## ビルドと実行
サンプルアプリのビルドにはJava11以上とMavenが必要です

```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
# Go into the sample
cd contrarian-microprofile-sample/01-restclient/server
# start junit test
mvn clean verify
```
