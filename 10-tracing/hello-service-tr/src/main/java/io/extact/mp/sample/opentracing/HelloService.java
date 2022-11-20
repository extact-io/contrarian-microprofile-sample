package io.extact.mp.sample.opentracing;

import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;

import io.opentracing.Tracer;

@ApplicationScoped
public class HelloService {

    @Inject
    @ConfigProperty(name = "hello.message", defaultValue = "Hello!")
    private String hello;

    @Inject
    private Tracer tracer;

    @Traced
    public String getHello() {
        tracer.activeSpan().log(Map.of("hello", hello));
        return hello;
    }
}
