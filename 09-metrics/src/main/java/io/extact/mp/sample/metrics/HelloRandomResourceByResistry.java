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

import org.eclipse.microprofile.metrics.ConcurrentGauge;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Gauge;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetadataBuilder;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.SimpleTimer;
import org.eclipse.microprofile.metrics.Tag;
import org.eclipse.microprofile.metrics.Timer;

@ApplicationScoped
@Path("/hello/reg")
public class HelloRandomResourceByResistry {

    private static final Random RANDOM = new Random();
    private static final List<String> HELLO_LIST = List.of("こんにちは", "Hello", "ニイハオ", "ボンジュール", "アンニョンハセヨ");
    private static final Tag JA_TAG = new Tag("lang", "ja");
    private static final Tag OTHER_TAG = new Tag("lang", "other");

    private AtomicInteger wordCount = new AtomicInteger(0);
    private MetricRegistry registry;

    @Inject
    public HelloRandomResourceByResistry(MetricRegistry registry) {
        this.registry = registry;
        registerGaugeIfAbsent();
    }

    public int getWordCount() {
        return wordCount.get();
    }

    @GET
    @Path("/counter/ja")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloCounterWithJa() {
        var hitWord = internalRandomHello();
        if (hitWord.equals("こんにちは")) {
            Counter jaCounter = getOrCreateJaCounter();
            jaCounter.inc();
        }
        return hitWord;
    }

    @GET
    @Path("/counter/ja-and-other")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloCounterWithJaAndOthers() {
        var hitWord = internalRandomHello();
        if (hitWord.equals("こんにちは")) {
            Counter jaCounter = getOrCreateWithTagCounter(JA_TAG);
            jaCounter.inc();
        } else {
            Counter otherCounter = getOrCreateWithTagCounter(OTHER_TAG);
            otherCounter.inc();
        }
        return hitWord;
    }

    @GET
    @Path("/meter")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithMeter() {
        Meter meter = getOrCreateMeter();
        meter.mark();
        return internalRandomHello();
    }

    @GET
    @Path("/timer")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithTimer() {
        try (var timer = getOrCreateTimer().time()) {
            return internalRandomHello();
        }
    }

    @GET
    @Path("/st")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithSimplyTimer() {
        try (var timer = getOrCreateSimpleTimer().time()) {
            return internalRandomHello();
        }
    }

    @GET
    @Path("/cg")
    @Produces(MediaType.TEXT_PLAIN)
    public String randomHelloWithConcurrentGauge() {
        ConcurrentGauge concurrentGauge = getOrCreateConcurrentGauge();
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

    private Counter getOrCreateJaCounter() {
        Metadata conterMetadata = new MetadataBuilder()
                .withName("helloCallCounter-reg")
                .withDisplayName("Counter Metrics By MetricRegistry")
                .withDescription("Number of hits on randomHello method")
                .withType(MetricType.COUNTER)
                .withUnit(MetricUnits.NONE)
                .build();
        return registry.counter(conterMetadata);
    }

    private Meter getOrCreateMeter() {
        Metadata meterMetadata = new MetadataBuilder()
                .withName("helloCallMeter-reg")
                .withDisplayName("Meter Metrics By MetricRegistry")
                .withDescription("Rate of events over time on randomHello method")
                .withType(MetricType.METERED)
                .withUnit(MetricUnits.PER_SECOND)
                .build();
        return registry.meter(meterMetadata);
    }

    private Timer getOrCreateTimer() {
        Metadata timerMetadata = new MetadataBuilder()
                .withName("helloCallTimer-reg")
                .withDisplayName("Timer Metrics By MetricRegistry")
                .withDescription("Both the rate that randomHello method is called and the distribution of its durationon")
                .withType(MetricType.TIMER)
                .withUnit(MetricUnits.NANOSECONDS)
                .build();
        return registry.timer(timerMetadata);
    }

    private SimpleTimer getOrCreateSimpleTimer() {
        Metadata simpleTimerMetadata = new MetadataBuilder()
                .withName("helloCallSimpleTimer-reg")
                .withDisplayName("SimpleTimer Metrics By MetricRegistry")
                .withDescription("simply timed")
                .withType(MetricType.SIMPLE_TIMER)
                .withUnit(MetricUnits.NANOSECONDS)
                .build();
        return registry.simpleTimer(simpleTimerMetadata);
    }

    private ConcurrentGauge getOrCreateConcurrentGauge() {
        Metadata concurrentGaugeMetadata = new MetadataBuilder()
                .withName("helloCallConcurrentGauge-reg")
                .withDisplayName("ConcurrentGauge Metrics By MetricRegistry")
                .withDescription("ConcurrentGauge")
                .withType(MetricType.CONCURRENT_GAUGE)
                .withUnit(MetricUnits.NONE)
                .build();
        return registry.concurrentGauge(concurrentGaugeMetadata);
    }

    private Gauge<Integer> registerGaugeIfAbsent() {
        Metadata gaugeMetadata = new MetadataBuilder()
                .withName("wordCountGauge-reg")
                .withDisplayName("Gauge Metrics By MetricRegistry")
                .withDescription("word count value")
                .withType(MetricType.GAUGE)
                .withUnit("characters")
                .build();
        return registry.gauge(gaugeMetadata, () -> getWordCount());
    }

    private Counter getOrCreateWithTagCounter(Tag tag) {
        Metadata withTagConterMetadata = new MetadataBuilder()
                .withName("withTagCounter-reg")
                .withType(MetricType.COUNTER)
                .withUnit(MetricUnits.NONE)
                .build();
        return registry.counter(withTagConterMetadata, tag);
    }
}
