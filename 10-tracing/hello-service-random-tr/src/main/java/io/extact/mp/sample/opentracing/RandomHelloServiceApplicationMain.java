package io.extact.mp.sample.opentracing;

import org.slf4j.bridge.SLF4JBridgeHandler;

public class RandomHelloServiceApplicationMain {
    public static void main(String[] args) {
        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // Helidonの起動
        io.helidon.microprofile.cdi.Main.main(args);
    }
}
