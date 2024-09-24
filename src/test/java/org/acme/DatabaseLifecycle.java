package org.acme;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.codehaus.plexus.util.CachedMap;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class DatabaseLifecycle implements QuarkusTestResourceLifecycleManager {
    
    private static PostgreSQLContainer<?> POSTGRESQL = new PostgreSQLContainer<>("postgres:14");

    @Override
    public Map<String, String> start() {
        POSTGRESQL.start();
        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.jdbc.url", POSTGRESQL.getJdbcUrl());
        properties.put("quarkus.datasource.username", POSTGRESQL.getUsername());
        properties.put("quarkus.datasource.password", POSTGRESQL.getPassword());
        return properties;
    }

    @Override
    public void stop() {
        if (POSTGRESQL != null) {
            POSTGRESQL.stop();
        }
    }
}
