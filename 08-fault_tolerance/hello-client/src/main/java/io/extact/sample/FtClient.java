package io.extact.sample;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Timeout(500)
public class FtClient {
    private static final Logger log = LoggerFactory.getLogger(FtClient.class);

    @Retry(maxRetries = 3, delay = 1000)
    public void execA(String param) {
        workA(param);
    }

    @Retry(maxRetries = 2, maxDuration = 100)
    public String execB() {
        return workB();
    }

    @Retry(maxRetries = 5)
    @Fallback(fallbackMethod = "fallback")
    public int execC(int count) {
        return workC();
    }

    public int fallback(int count) {
        return 999;
    }

    private void workA(String param) {
        log.info("call executeA:{}", param);
        sleep(1000); // 通常TimeoutExceptionによりretryが行われる
        throw new IllegalStateException("workA");
    }

    private String workB() {
        log.info("call executeB");
        sleep(100); // 通常maxDurationでretryが行われない
        throw new IllegalStateException("workB");
    }

    private int workC() {
        log.info("call executeC"); // 通常下の例外でリトライが行われる
        throw new IllegalStateException("workC");
    }

    private void sleep(long value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
