package io.extact.sample;

import jakarta.enterprise.inject.spi.CDI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class FtClientMain {

    private static final Logger log = LoggerFactory.getLogger(FtClientMain.class);

    public static void main(String[] args) {
        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // Helidonの起動
        io.helidon.microprofile.cdi.Main.main(args);

        FtClient client = CDI.current().select(FtClient.class).get();
        try {
            client.execA("hello!");
            log.info("end execA:SUCCESS");
        } catch (Exception e) {
            log.info("end execA:{}({})", e.getClass().getSimpleName(), e.getMessage());
        }
        try {
            client.execB();
            log.info("end execB:SUCCESS");
        } catch (Exception e) {
            log.info("end execB:{}({})", e.getClass().getSimpleName(), e.getMessage());
        }
        try {
            client.execC(3);
            log.info("end execC:SUCCESS");
        } catch (Exception e) {
            log.info("end execC:{}({})", e.getClass().getSimpleName(), e.getMessage());
        }

        System.exit(0);
    }
}
