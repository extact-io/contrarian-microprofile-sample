package io.extact.mp.sample.metrics;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;

@ApplicationScoped
@Path("/hello")
public class HelloRandomResource {

    private static final Random RANDOM = new Random();
    private static final List<String> HELLO_LIST = List.of("こんにちは", "Hello", "ニイハオ", "ボンジュール", "アンニョンハセヨ");

    private AtomicInteger wordCount = new AtomicInteger(0);

    @GET
    @Path("/simple")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted
    public String simpleHello() {
        return "Hello!";
    }

    @Gauge(name = "wordCountGauge", absolute = true, unit = MetricUnits.NONE)
    public int getWordCount() {
        return wordCount.get();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "helloCallCounter", absolute = true)
    @Metered(name = "helloCallMeter", absolute = true)
    @Timed(name = "helloCallTimer", absolute = true, unit = MetricUnits.MILLISECONDS)
    @SimplyTimed(name = "helloCallSimpleTimer", absolute = true, unit = MetricUnits.MILLISECONDS)
    @ConcurrentGauge(name = "helloCallConcurrentGauge", absolute = true)
    public String randomHello() {
        var rand = RANDOM.nextInt(5);
        var hitWord = HELLO_LIST.get(rand);
        wordCount.getAndAdd(hitWord.length());
        sleep(rand); // レスポンスタイムをバラつかせる
        return hitWord;
    }

    private void sleep(long t) {
        try {
            Thread.sleep(t * 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
