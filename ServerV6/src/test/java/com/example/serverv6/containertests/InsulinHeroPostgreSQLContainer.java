package com.example.serverv6.containertests;

import org.testcontainers.containers.PostgreSQLContainer;

public class InsulinHeroPostgreSQLContainer extends PostgreSQLContainer<InsulinHeroPostgreSQLContainer> {

    private static final String IMAGE_VERSION = "postgres:11.1";
    private static InsulinHeroPostgreSQLContainer container;

    private InsulinHeroPostgreSQLContainer() {
        super(IMAGE_VERSION);
    }

    public static InsulinHeroPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new InsulinHeroPostgreSQLContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}
