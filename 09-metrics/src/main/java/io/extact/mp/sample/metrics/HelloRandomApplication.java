package io.extact.mp.sample.metrics;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
public class HelloRandomApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(HelloRandomResource.class,
                HelloRandomResourceByAnnotation.class,
                HelloRandomResourceByResistry.class);
    }
}
