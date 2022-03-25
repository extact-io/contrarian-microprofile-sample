package io.extact.mp.sample.openapi;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import io.extact.mp.sample.openapi.resource.PersonResourceImpl;

@OpenAPIDefinition(info =
    @Info(title = "MicroProfile OpneAPI Sample", version = "0.0.1-SNAPSHOT",
        contact = @Contact(name = "課外活動",
            url = "https://extact-io.github.io/")))
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
