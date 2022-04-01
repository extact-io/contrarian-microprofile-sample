# 第6回 お手軽便利MicroProfile Config

## contents 
|章|MainClass|
| ---------- | --- |
| 設定の読み込み |SimpleSampleMain|
| 設定値の型変換 |ConverterSampleMain|
| CDIインテグレーション |CdiSampleBeanMain|
| 設定形式の抽象化 |ConfigAbstractionMain|
| 複数設定源のマージ |ConfigMergeMain|
| 設定源の優先度付けを利用した応用例 |AdvancedExampleMain|


## ビルドと実行
サンプルアプリのビルドにはJava11以上とMavenが必要です

```shell
# Clone this repository
git clone https://github.com/extact-io/contrarian-microprofile-sample.git
# Go into the sample
cd 02-config
# build application
mvn clean package
# start application
java -cp "target/libs/*" io.extact.mp.sample.config.<MainClass>
```

