package io.extact.mp.sample.opentracing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletionStage;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

@Path("/hello")
@RegisterClientHeaders(TimestampHeaderFactory.class)
//@ClientHeaderParam(name="X-Requested-Timestamp", value="{generateTimestamp}")
public interface HelloRestClient extends AutoCloseable {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String hello();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<String> asyncHello();

    default String generateTimestamp() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        return formatter.format(LocalDateTime.now());
    }
}
