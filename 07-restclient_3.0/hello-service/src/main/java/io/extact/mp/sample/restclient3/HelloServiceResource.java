package io.extact.mp.sample.restclient3;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
@Path("/hello")
public class HelloServiceResource {

    private static Logger LOG = LoggerFactory.getLogger(HelloServiceResource.class);

    @Inject
    @ConfigProperty(name = "hello.message", defaultValue = "Hello!")
    private String hello;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@HeaderParam("X-Requested-Timestamp") String timestamp) throws Exception {
        LOG.info("Requested-Timestamp:{}/{}-service:request recieved", timestamp, this.hello);
        Thread.sleep(1000L);
        return this.hello;
    }
}
