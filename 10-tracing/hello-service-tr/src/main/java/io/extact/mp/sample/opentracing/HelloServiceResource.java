package io.extact.mp.sample.opentracing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
@Path("/hello")
public class HelloServiceResource {

    private static Logger LOG = LoggerFactory.getLogger(HelloServiceResource.class);

//    @Inject
//    @ConfigProperty(name = "hello.message", defaultValue = "Hello!")
//    private String hello;

    private HelloService service;

    @Inject
    public HelloServiceResource(HelloService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@HeaderParam("X-Requested-Timestamp") String timestamp) throws Exception {
        LOG.info("Requested-Timestamp:{}/{}-service:request recieved", timestamp, "");
        Thread.sleep(1000L);
//        return this.hello;
        return service.getHello();
    }
}
