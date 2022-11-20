package io.extact.mp.sample.opentracing;

import java.util.Random;

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
public class RandomHelloServiceResource {

    private static Logger LOG = LoggerFactory.getLogger(RandomHelloServiceResource.class);
    private static Random RANDOM = new Random();

    private HelloRepository repository;

    @Inject
    public RandomHelloServiceResource(HelloRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@HeaderParam("X-Requested-Timestamp") String timestamp) throws Exception {
        LOG.info("Requested-Timestamp:{}/{}-service:request recieved", timestamp, "RANDOM");
        Thread.sleep(1000L);
        // DBに登録されているIDが0～4の挨拶文字列を取得
        return repository.get(RANDOM.nextInt(5));
    }
}
