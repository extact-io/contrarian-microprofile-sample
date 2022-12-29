package io.extact.mp.sample.jwt;

import org.slf4j.bridge.SLF4JBridgeHandler;

public class ProductApplicationMain {
    public static void main(String[] args) {
        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // AuthUserの指定切り替え
        String prop = System.getProperty("authuser");
        if (prop == null) { // default setting
            System.setProperty("authuser", "producer");
        }
        // Helidonの起動
        io.helidon.microprofile.cdi.Main.main(args);
    }
}
