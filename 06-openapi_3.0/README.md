# 第9回 MicroProfile OpenAPI 3.0の新機能と既存機能の比較

## contents 
|章|定義場所|
| ---------- | --- |
| 特定クラスに対するスキーマ指定の利用法 |[/META-INF/microprofile-config.properties](src/main/resources/META-INF/microprofile-config.properties)|
| @OpenAPIDefinitionによる共通項目の定義 |[PersonApplication](src/main/java/io/extact/mp/sample/openapi3/PersonApplication.java)|
| OASFactoryを使ったプログラムによるOAS情報の定義 |[LocalDateApiModelReader](src/main/java/io/extact/mp/sample/openapi3/reader/LocalDateApiModelReader.java)|
| OpenAPIドキュメントによる共通項目の定義 |[/META-INF/openapi.yaml](src/main/resources/META-INF/_openapi.yaml)|
| @SchemaPropertyの導入 |[Person](src/main/java/io/extact/mp/sample/openapi3/resource/Person.java)|

LocalDateに対するスキーマ情報の定義はいずれもコメントアウトで無効化しています。
動作を確認する場合はコメントアウトを解除してください。なお、`openapi.yaml`はファイル名を`_openapi.yaml`として無効化してるので有効化する際はリネームしてください。

## ビルドと実行
サンプルアプリのビルドにはJava17以上とMavenが必要です

```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
# Go into the sample
cd 06-openapi_3.0
# build application
mvn clean package
# start application
java -jar target/openapi3-sample.jar
```
