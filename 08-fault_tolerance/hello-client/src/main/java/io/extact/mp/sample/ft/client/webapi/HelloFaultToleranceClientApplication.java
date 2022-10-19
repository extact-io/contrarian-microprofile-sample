package io.extact.mp.sample.ft.client.webapi;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
public class HelloFaultToleranceClientApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(HelloFaultToleranceClientResource.class);
    }
}
