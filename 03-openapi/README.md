# 第5回 コードが仕様の源泉MicroProfile OpenAPI のサンプルアプリ

## contents 
|ディレクトリ|内容|
| ---------- | --- |
| [01_before](01_before/) |MicroProfile OpenAPIのアノテーションを付ける前|
| [02_after](02_after/) |`01_before`にMicroProfile OpenAPIのアノテーションを付けた完成版|

## ビルドと実行
サンプルアプリのビルドにはJava11以上とMavenが必要です

- `01_before`の例
```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
# Go into the sample
cd 03-openapi/before
# build application
mvn clean package
# start application
java -jar target/openapi-sample.jar
```

