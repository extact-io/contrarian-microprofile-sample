package io.extact.mp.sample.health.check;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
@Readiness
public class DbHealthCheck implements HealthCheck {

    private static final String PING_SQL = "SELECT 1"; // for H2 database
    @PersistenceContext
    private EntityManager em;

    @Override
    public HealthCheckResponse call() {
        var responseBuilder = HealthCheckResponse.named(this.getClass().getSimpleName());
        try {
            em.createNativeQuery(PING_SQL).getSingleResult();
            return responseBuilder.up().build();
        } catch (Exception e) {
            return responseBuilder.down().build();
        }
    }
}
