package edu.my.health;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

@Liveness
public class DatabaseCheck implements HealthCheck {

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String db_url;
    @ConfigProperty(name = "quarkus.datasource.username")
    String db_user;
    @ConfigProperty(name = "quarkus.datasource.password")
    String db_passwd;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private boolean isConnected() {
        try (Connection connection = DriverManager.getConnection(db_url, db_user, db_passwd)) {
            if (connection != null)
                return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public HealthCheckResponse call() {
        if (isConnected()){
            return HealthCheckResponse.up("Database is alive :)");
        }
        return HealthCheckResponse.down("Database is dead :'(");
    }
}
