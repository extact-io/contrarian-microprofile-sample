package io.extact.mp.sample.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ConfigAbstractionMain {

    public static void main(String[] args) {
        // Configインスタンスの取得
        Config config = ConfigProvider.getConfig();
        // 設定値の取得
        String name = config.getValue("person.name", String.class);
        int age = config.getValue("person.age", int.class);
        String address = config.getValue("person.address", String.class);
        // 取得値の出力
        System.out.printf("name:%s, age:%s, address:%s\n", name, age, address);
    }
}
