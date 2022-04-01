package io.extact.mp.sample.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class AdvancedExampleMain {

    public static void main(String[] args) {
        // Configインスタンスの取得
        Config config = ConfigProvider.getConfig();

        // 設定値の取得
        String protocol = config.getValue("sample.server.protocol", String.class);
        String host = config.getValue("sample.server.host", String.class);
        int port = config.getValue("sample.server.port", int.class);

        // 取得値の出力
        System.out.printf("> %s://%s:%s\n", protocol, host, port);
    }
}
