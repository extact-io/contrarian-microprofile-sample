package io.extact.mp.sample.health.webapi;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
public class AppStatusApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(AppStatusResource.class);
    }
}
