package edu.my.health;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class DatabaseCheck implements HealthCheck {
    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder;

        try {
            entityManager.createQuery("SELECT 1").getResultList();
            responseBuilder = HealthCheckResponse.named("Database is alive :)").up();
        } catch (Exception e) {
            responseBuilder = HealthCheckResponse.named("Database is dead :'(").down();
        }

        return responseBuilder.build();
    }
}
