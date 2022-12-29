# 第17回 MicroProfile JWT Authがやってくれること・できること

## contents 
|サービス|フォルダ|
| ---------- | --- |
| SimpleIDProvider |[simple-idprovider](simple-idprovider/) |
| Productサービス |[product-service](product-service/) |

## ビルドと実行
サンプルアプリのビルドにはJava17以上とMavenが必要です

### リポジトリclone
```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
```

### SimpleIDProviderのビルド
```shell
# Go into SimpleIDProvider
cd contrarian-microprofile-sample/11-jwt/simple-idprovider
# build application
mvn clean package
```
### Productサービスのビルドと実行
```shell
# Go into Product Service
cd contrarian-microprofile-sample/11-jwt/product-service/
# build application
mvn clean package
# start application
java -jar target/product-service.jar
```

## 実行
[こちら](http://localhost:8080/msa/mp/cntrn17-mp-jwt/#%E3%82%B5%E3%83%B3%E3%83%97%E3%83%AB%E3%82%A2%E3%83%97%E3%83%AA%E3%81%AE%E5%8B%95%E4%BD%9C%E3%82%A4%E3%83%A1%E3%83%BC%E3%82%B8)の記事を参照
