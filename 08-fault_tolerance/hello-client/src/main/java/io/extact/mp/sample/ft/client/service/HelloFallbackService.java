package io.extact.mp.sample.ft.client.service;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;

import io.extact.mp.sample.ft.client.exception.RetryableException;

@ApplicationScoped
public class HelloFallbackService implements FallbackHandler<String> {
    public String handle(ExecutionContext context) {
        if (context.getFailure() instanceof RetryableException) {
            return "もう一回聞いて";
        }
        if (context.getFailure() instanceof TimeoutException) {
            return "チョッと待って";
        }
        return "沈黙..";
    }
}
