package io.extact.mp.sample.ft.service;

import java.util.Map;
import java.util.function.Supplier;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.extact.mp.sample.ft.service.exception.FatalException;
import io.extact.mp.sample.ft.service.exception.RetryableException;
import io.extact.mp.sample.ft.service.exception.SkipException;

@RequestScoped
@Path("/hello")
public class HelloServiceResource {

    private static Logger LOG = LoggerFactory.getLogger(HelloServiceResource.class);

    private Map<String, Supplier<String>> actionMapper = Map.of(
                "success", this::success,
                "sleep", this::sleep,
                "longSleep", this::longSleep,
                "throwRetryable", this::throwRetryable,
                "throwSkip", this::throwSkip,
                "throwFatal", this::throwFatal
            );

    @Inject
    @ConfigProperty(name = "hello.message", defaultValue = "Hello!")
    private String hello;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@HeaderParam("X-Requested-Timestamp") String timestamp, @QueryParam("action") String action) {
        LOG.info("★{} -> IN : Client-Requested-Timestamp:{}", action, timestamp);
        var executor = actionMapper.get(action);
        try {
            var ret = executor.get();
            LOG.info("★{} -> OUT", action);
            return ret;
        } catch (Exception e) {
            LOG.info("★{} -> OUT", action);
            throw e;
        }
    }

    public String success() {
        return this.hello;
    }

    public String sleep() {
        doSleep(1000);
        return this.hello;
    }

    public String longSleep() {
        doSleep(10000);
        return this.hello;
    }

    public String throwRetryable() {
        doSleep(1000);
        throw new RetryableException();
    }

    public String throwSkip() {
        doSleep(1000);
        throw new SkipException();
    }

    public String throwFatal() {
        doSleep(1000);
        throw new FatalException();
    }

    private void doSleep(long msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
