# MicroProfile Fault Tolerance
> 第12回 MicroProfile Fault Tolerance(1) - 例で理解する基本機能編  
> 第13回 MicroProfile Fault Tolerance(2) - 例で理解する非同期編  
> 第14回 MicroProfile Fault Tolerance(3) - 例で理解する設定編  

## contents 
|記事|本文中に登場コード|
| ---------- | --- |
|[第12回 例で理解する基本機能編](https://developer.mamezou-tech.com/msa/mp/cntrn12-mp-faulttolerance1/)|[HelloFaultToleranceClientResource](hello-client/src/main/java/io/extact/mp/sample/ft/client/webapi/HelloFaultToleranceClientResource.java) |
||[HelloFautlToleranceService](hello-client/src/main/java/io/extact/mp/sample/ft/client/service/HelloFautlToleranceService.java) |
||[HelloFallbackService](hello-client/src/main/java/io/extact/mp/sample/ft/client/service/HelloFallbackService.java) |
||[HelloRestClient](hello-client/src/main/java/io/extact/mp/sample/ft/client/external/HelloRestClient.java) |
||[HelloServiceResource](hello-service-ft/src/main/java/io/extact/mp/sample/ft/service/HelloServiceResource.java) |
|[第13回 例で理解する非同期編](https://developer.mamezou-tech.com/msa/mp/cntrn13-mp-faulttolerance2)|[HelloFaultToleranceClientResource](hello-client/src/main/java/io/extact/mp/sample/ft/client/webapi/HelloFaultToleranceClientResource.java) |
||[HelloFautlToleranceService](hello-client/src/main/java/io/extact/mp/sample/ft/client/service/HelloFautlToleranceService.java) |
||[HelloRestClient](hello-client/src/main/java/io/extact/mp/sample/ft/client/external/HelloRestClient.java) |
|[第14回 例で理解する設定編](https://developer.mamezou-tech.com/msa/mp/cntrn14-mp-faulttolerance3)|[HelloFtClient](hello-client/src/main/java/io/extact/sample/FtClient.java) |
||[microprofile-config.properties](hello-client/src/main/resources/META-INF/microprofile-config.properties) |


## ビルドと実行
サンプルアプリのビルドにはJava17以上とMavenが必要です

### リポジトリclone
```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
```

### HelloClientアプリのビルドと実行
```shell
# Go into HelloAggrega Service
cd contrarian-microprofile-sample/08-fault_tolerance/hello-client
# build application
mvn clean package
# start application
java -jar target/hello-fault-tolerance-client.jar &
```
### Helloサービスのビルドと実行
```shell
# Go into Hello Service
cd contrarian-microprofile-sample/08-fault_tolerance/hello-service-ft
# build application
mvn clean package
# start application
java -jar target/hello-service-ft.jar &
```

### HelloClientアプリの呼び出し
- [HelloFautlToleranceService](hello-client/src/main/java/io/extact/mp/sample/ft/client/service/HelloFautlToleranceService.java)のJavadoc参照
