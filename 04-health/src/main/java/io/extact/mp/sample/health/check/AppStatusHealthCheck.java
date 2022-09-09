package io.extact.mp.sample.health.check;

import java.time.LocalDateTime;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.Startup;

import io.extact.mp.sample.health.status.AppStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Startup
@Readiness
public class AppStatusHealthCheck implements HealthCheck {

    private AppStatus appStatus;

    @Inject
    public AppStatusHealthCheck(AppStatus status) {
        this.appStatus = status;
    }

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .named(this.getClass().getSimpleName())
                .status(appStatus.isOpen())
                .withData("appStatus", appStatus.isOpen() ? "OPEN" : "CLOSE")
                .withData("checkTime", LocalDateTime.now().toString())
                .build();
    }
}
