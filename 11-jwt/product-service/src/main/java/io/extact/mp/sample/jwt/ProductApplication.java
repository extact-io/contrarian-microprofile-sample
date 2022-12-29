package io.extact.mp.sample.jwt;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;

import org.eclipse.microprofile.auth.LoginConfig;

import io.extact.mp.sample.jwt.resource.ProductResource;

@LoginConfig(authMethod = "MP-JWT")
@ApplicationScoped
public class ProductApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                ProductResource.class,
                ProductExceptionMapper.class);
    }
}
