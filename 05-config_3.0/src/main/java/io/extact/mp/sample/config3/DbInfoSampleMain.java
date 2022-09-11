package io.extact.mp.sample.config3;

import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.slf4j.bridge.SLF4JBridgeHandler;

import jakarta.enterprise.inject.spi.CDI;

public class DbInfoSampleMain {
    public static void main(String[] args) {

        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // Helidonの起動
        io.helidon.microprofile.cdi.Main.main(args);

        DbInfo sampleDbInfo = CDI.current()
                .select(DbInfo.class, ConfigProperties.Literal.NO_PREFIX)
                .get();
        System.out.println("sample.db=>" + sampleDbInfo);

        DbInfo testDbInfo = CDI.current()
                .select(DbInfo.class, ConfigProperties.Literal.of("test.db"))
                .get();
        System.out.println("test.db=>" + testDbInfo);

        System.exit(0);
    }
}
