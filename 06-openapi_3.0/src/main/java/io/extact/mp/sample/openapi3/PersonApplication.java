package io.extact.mp.sample.openapi3;

import java.util.Set;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import io.extact.mp.sample.openapi3.resource.PersonResourceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
    info = @Info(
        title = "MicroProfile OpneAPI Sample",
        version = "0.0.1-SNAPSHOT",
        contact = @Contact(
                name = "課外活動",
                url = "https://extact-io.github.io/"))
//    , components = @Components(
//        schemas = {
//            @Schema(
//                name = "LocalDate",
//                description = "app date format(annotation)",
//                example = "20210314",
//                format = "yyyyMMdd",
//                implementation = String.class)})
    )
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
