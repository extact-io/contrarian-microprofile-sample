package io.extact.mp.sample.config3;

import org.slf4j.bridge.SLF4JBridgeHandler;

import jakarta.enterprise.inject.spi.CDI;

public class ConfigBeanSampleMain {

    public static void main(String[] args) {
        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // Helidonの起動
        io.helidon.microprofile.cdi.Main.main(args);

        var configBean = CDI.current().select(ConfigBean.class).get();

        DbInfo sampleDbInfo = configBean.getSampleDbInfo();
        System.out.println("sample.db=>" + sampleDbInfo);

        DbInfo testDbInfo = configBean.getTestDbInfo();
        System.out.println("test.db=>" + testDbInfo);

        System.exit(0);
    }
}
