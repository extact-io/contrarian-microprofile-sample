package io.extact.mp.sample.ft.client.external;

import java.util.concurrent.CompletionStage;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:7002")
@RegisterClientHeaders(TimestampHeaderFactory.class)
@RegisterProvider(HelloClientExceptionMapper.class)
@Path("/hello")
public interface HelloRestClient{

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String hello(@QueryParam("action") String action);


    @GET
    @Path("/sleep-1sec")
    @Produces(MediaType.TEXT_PLAIN)
    String hello_sleep_1sec();

    @GET
    @Path("/throw-retryable")
    @Produces(MediaType.TEXT_PLAIN)
    String hello_throw_retryable();

    @GET
    @Path("/throw-fatal")
    @Produces(MediaType.TEXT_PLAIN)
    String hello_throw_fatal();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<String> asyncHello(@QueryParam("action") String action);
}
