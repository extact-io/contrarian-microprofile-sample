package io.extact.mp.sample.ft.client.webapi;

import java.net.SocketException;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.extact.mp.sample.ft.client.service.HelloFautlToleranceService;
import io.extact.mp.sample.ft.client.webapi.StartEndLogInterceptor.StartEndLog;

@RequestScoped
@Path("/client")
@StartEndLog
public class HelloFaultToleranceClientResource {

    private static final Logger log = LoggerFactory.getLogger(HelloFaultToleranceClientResource.class);

    private HelloFautlToleranceService helloService;

    @Inject
    public HelloFaultToleranceClientResource(HelloFautlToleranceService hellService) {
        this.helloService = hellService;
    }

    @GET
    @Path("/01")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern01() {
        return helloService.hello_timeout();
    }

    @GET
    @Path("/02")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern02() {
        return helloService.hello_retry_by_timeout();
    }

    @GET
    @Path("/03")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern03() {
        return helloService.hello_retry_with_delay();
    }

    @GET
    @Path("/04")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern04() {
        return helloService.hello_retry_with_jitterDelay();
    }

    @GET
    @Path("/05")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern05() {
        return helloService.hello_retry_with_maxDuration();
    }

    @GET
    @Path("/06")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern06() {
        return helloService.hello_retryOn_retryable();
    }

    @GET
    @Path("/07")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern07() {
        return helloService.hello_abortOn();
    }

    @GET
    @Path("/08")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern08() throws SocketException {
        return helloService.hello_socketException();
    }

    @GET
    @Path("/09")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern09() {
        return helloService.hello_fallback_by_cdi();
    }

    @GET
    @Path("/10")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern10() {
        return helloService.hello_fallback_by_inline();
    }

    @GET
    @Path("/11")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern11() {
        return helloService.hello_fallback_with_criteria();
    }

    @GET
    @Path("/12")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern12(@QueryParam("action") String action) {
        return helloService.hello_circuitBreaker(action);
    }

    @GET
    @Path("/13")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern13(@QueryParam("action") String action) {
        return helloService.hello_circuitBreaker_with_retry(action);
    }

    @GET
    @Path("/14")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern14(@QueryParam("action") String action) {
        return helloService.hello_circuitBreaker_with_skipOn(action);
    }

    @GET
    @Path("/15")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern15() throws Exception {
        return helloService.hello_bulkhead();
    }

    @GET
    @Path("/16")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern16() throws Exception {
        log.info("execute hello_16");
        var ret = helloService.hello_async_with_bulkhead().get();
        log.info("end hello_16");
        return ret;
    }

    @GET
    @Path("/17")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern17() throws Exception {
        return helloService.hello_async_with_timeout() // 1.
                .toCompletableFuture() // 2.
                .get(); // 3.
    }

    @GET
    @Path("/18")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern18() throws Exception {
        return helloService.hello_async_with_retry().get();
    }

    @GET
    @Path("/19")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPattern19(@QueryParam("action") String action) {
        return helloService.hello_config_override(action);

    }

    @GET
    @Path("/99")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPatternEx(@QueryParam("action") String action) throws Exception {
        return helloService.hello_restclinet_async(action).toCompletableFuture().get();
    }
}
