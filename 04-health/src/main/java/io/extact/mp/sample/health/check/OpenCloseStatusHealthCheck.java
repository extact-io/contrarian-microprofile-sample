package io.extact.mp.sample.health.check;

import java.time.LocalDateTime;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.Startup;

import io.extact.mp.sample.health.status.OpenCloseStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Startup
@Readiness
public class OpenCloseStatusHealthCheck implements HealthCheck {

    private OpenCloseStatus openClosestatus;

    @Inject
    public OpenCloseStatusHealthCheck(OpenCloseStatus status) {
        this.openClosestatus = status;
    }

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .named(this.getClass().getSimpleName())
                .status(openClosestatus.isOpen())
                .withData("open/close", openClosestatus.isOpen() ? "OPEN" : "CLOSE")
                .withData("checkTime", LocalDateTime.now().toString())
                .build();
    }
}
