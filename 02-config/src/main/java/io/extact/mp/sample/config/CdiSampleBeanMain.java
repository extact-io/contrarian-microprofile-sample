package io.extact.mp.sample.config;

import javax.enterprise.inject.spi.CDI;

import org.slf4j.bridge.SLF4JBridgeHandler;

public class CdiSampleBeanMain {

    public static void main(String[] args) {
        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Helidonの起動
        io.helidon.microprofile.cdi.Main.main(args);

        // dump出力
        var bean = CDI.current().select(CdiSampleBean.class).get();
        bean.dumpFieldInjectionConfig();
        bean.dumpConfigProperties();

        System.exit(0);
    }
}
