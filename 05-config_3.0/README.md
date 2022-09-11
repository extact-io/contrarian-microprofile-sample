# 第8回 Microprofile Config 3.0へのキャッチアップ

## 起動するMainClass
|用途|MainClass|
| ---------- | --- |
| CDI.select()でConfigを取得する |DbInfoSampleMain|
| @InjectでConfigを取得する |ConfigBeanSampleMain|


## ビルドと実行
サンプルアプリのビルドにはJava17以上とMavenが必要です

```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
# Go into the sample
cd 05-config_3.0
# build application
mvn clean package
# start application
java -cp "target/libs/*" io.extact.mp.sample.config3.<MainClass>
# start application with config profile
java -Dmp.config.profile=<profile> "target/libs/*" io.extact.mp.sample.config3.<MainClass>
```

## 「少しひねりを加えた設定ファイルの切り替え」のサンプルを実行する場合
META-INFに配置するpropertiesファイルを切り替える必要があるため、以下のようにMavenのプロファイルを指定してビルドし直します

```shell
# rebuild application
mvn -Poption clean package
```

実行方法はすべて他と同じです
