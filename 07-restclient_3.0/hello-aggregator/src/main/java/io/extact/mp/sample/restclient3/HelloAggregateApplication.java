package io.extact.mp.sample.restclient3;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
public class HelloAggregateApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(HelloAggregateResource.class);
    }
}
