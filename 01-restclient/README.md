# 第7回 らくらくMicroProfile RestClient

## contents 
|章|プロジェクト|MainClass|
| --- | --- | --- |
| 比較に利用するREST API |server|PersonApplicationMain|
| JAX-RSのClient APIによる実装 |client|PersonServiceMain|
| MicroProfile RestClientによる実装 |client|PersonServiceMain|

## ビルドと実行
サンプルアプリのビルドにはJava11以上とMavenが必要です

### RESTアプリ(サーバアプリ)のビルドと実行
```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
# Go into the sample
cd 01-restclient/server
# build application
mvn clean package -DskipTests=true
# start rest application
java -jar target/restclient-sample-server.jar
```

### クライアントアプリのビルドと起動
利用するCDI Beanの実装は起動パラメータの指定で切り替わります
|利用するCDI Bean|指定パラメータ|
| ---------- | --- |
| JaxrsPersonService.class |`jaxrs`|
| RestClientPersonService.class |`restclient`|

RESTアプリを起動したまま別のシェルで下記を実行します
```shell
# Go into the sample
cd /path/to/your-clone-dir/client
# build application
mvn clean package
# start client application using JaxrsPersonService.class
java -jar target/restclient-sample-client.jar jaxrs
# start client application using RestClientPersonService.class
java -jar target/restclient-sample-client.jar restclient
```
