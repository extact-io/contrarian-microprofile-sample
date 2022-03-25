package io.extact.mp.sample.openapi;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.extact.mp.sample.openapi.resource.PersonResourceImpl;

@ApplicationPath("api")
public class PersonApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                PersonResourceImpl.class,
                PersonExceptionMapper.class);
    }
}
