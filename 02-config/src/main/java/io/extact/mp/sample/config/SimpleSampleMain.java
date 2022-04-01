package io.extact.mp.sample.config;

import java.util.Optional;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class SimpleSampleMain {

    public static void main(String[] args) {
        // Configインスタンスの取得
        Config config = ConfigProvider.getConfig();
        
        // propertiesファイルの設定値の取得（キー名区切りなし）
        String name = config.getValue("name", String.class);
        // propertiesファイルの設定値の取得（キー名区切りあり）
        String baz = config.getValue("foo.bar.baz", String.class);
        // どこにも設定されていない設定値
        Optional<String> address = config.getOptionalValue("address", String.class);
        // システムプロパティの設定値の取得
        String version = config.getValue("java.version", String.class);
        // 環境変数の設定値の取得
        Optional<String> envUserName  = config.getOptionalValue("USERNAME", String.class);

        // 取得値の出力
        System.out.println("name:" + name);
        System.out.println("foo.bar.baz:" + baz);
        System.out.println("address:" + address.orElse("empty"));
        System.out.println("java.version:" + version);
        System.out.println("USERNAME:" + envUserName.orElse("no def"));
    }
}
