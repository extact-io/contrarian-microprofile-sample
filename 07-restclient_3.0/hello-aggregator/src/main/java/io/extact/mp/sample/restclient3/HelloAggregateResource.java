package io.extact.mp.sample.restclient3;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
@Path("/aggregate")
public class HelloAggregateResource {
    private static Logger LOG = LoggerFactory.getLogger(HelloAggregateResource.class);
    private static final List<String> HELLO_SERVICE_URLS = List.of(
                "http://localhost:7002", // japanese hello service
                "http://localhost:7003", // english hello service
                "http://localhost:7004"  // chinese hello service
            );
    private List<HelloRestClient> helloClients;

    @PostConstruct
    public void init() {
        // URLごとのRestClientインスタンスを生成
        helloClients = HELLO_SERVICE_URLS.stream()
                .map(url ->
                    RestClientBuilder.newBuilder()
                        .baseUri(URI.create(url))
                        .build(HelloRestClient.class))
                .toList();
    }

    @GET
    @Path("/sync")
    @Produces(MediaType.TEXT_PLAIN)
    public String aggregateHello() {
        return helloClients.stream()
                .map(HelloRestClient::hello)
                .collect(Collectors.joining(", "));
    }

    @GET
    @Path("/async")
    @Produces(MediaType.TEXT_PLAIN)
    public String asyncAggregateHello() {

        @SuppressWarnings({ "unchecked" })
        final CompletableFuture<String>[] futures = helloClients.stream()
                .map(HelloRestClient::asyncHello)
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> promise = CompletableFuture.allOf(futures);

        return promise.thenApply(dummy ->
            Stream.of(futures)
                    .map(CompletableFuture::join)
                    .collect(Collectors.joining(", "))
        ).join();
    }

    @PreDestroy
    public void destroy() {
        // RestClientインスタンスに対するclose
        if (helloClients != null) {
            helloClients.forEach(client -> {
                try {
                    client.close();
                } catch (Exception e) {
                    LOG.warn("exception occurred during close processing..", e);
                }
            });
        }
    }
}
