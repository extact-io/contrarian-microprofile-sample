package io.extact.mp.sample.metrics;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.SimpleTimer;
import org.eclipse.microprofile.metrics.Timer;
import org.eclipse.microprofile.metrics.annotation.Metric;

@ApplicationScoped
@Path("/hello/antn")
public class HelloRandomResourceByAnnotation {

    private static final Random RANDOM = new Random();
    private static final List<String> HELLO_LIST = List.of("こんにちは", "Hello", "ニイハオ", "ボンジュール", "アンニョンハセヨ");

    private AtomicInteger wordCount = new AtomicInteger(0);

    @Inject
    @Metric(name = "helloCallCounter-antn", absolute = true)
    private Counter jaCounter;
    @Inject
    @Metric(name = "helloCallMeter-antn", absolute = true)
    private Meter meter;
    @Inject
    @Metric(name = "helloCallTimer-antn", absolute = true, unit = MetricUnits.MILLISECONDS)
    private Timer timer;
    @Inject
    @Metric(name = "helloCallSimpleTimer-antn", absolute = true, unit = MetricUnits.MILLISECONDS)
    private SimpleTimer simpleTimer;
    @Inject
    @Metric(name = "helloCallConcurrentGauge-antn", absolute = true)
    private org.eclipse.microprofile.metrics.ConcurrentGauge concurrentGauge;

    @GET
    @Path("/counter")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithCounter() {
        var hitWord = internalRandomHello();
        if (hitWord.equals("こんにちは")) {
            jaCounter.inc();
        }
        return hitWord;
    }

    @GET
    @Path("/meter")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithMeter() {
        meter.mark();
        return internalRandomHello();
    }

    @GET
    @Path("/timer")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithTimer() {
        try (var cTimer = timer.time()) {
            return internalRandomHello();
        }
    }

    @GET
    @Path("/st")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithSimplyTimer() {
        try (var cTimer = simpleTimer.time()) {
            return internalRandomHello();
        }
    }

    @GET
    @Path("/cg")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithConcurrentGauge() {
        try {
            concurrentGauge.inc();
            sleep(300);
            return internalRandomHello();
        } finally {
            concurrentGauge.dec();
        }
    }

    private String internalRandomHello() {
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
