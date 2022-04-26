package io.extact.mp.sample.restclient;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.extact.mp.sample.restclient.resource.PersonResourceImpl;

@ApplicationScoped
@ApplicationPath("api")
public class PersonApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                PersonResourceImpl.class,
                PersonExceptionMapper.class);
    }
}
