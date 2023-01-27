package com.example.serverv6;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

@Configuration
@PropertySource("classpath:application.properties")
public class TestingDatabaseConfig {

    @Value("testing.database.host")
    private final String DB_HOST =
            Objects.requireNonNullElse(System.getenv("DB_HOST"), "localhost");

    private final String DB_PORT =
            Objects.requireNonNullElse(System.getenv("DB_PORT"), "5432");

    @Value("testing.database.name")
    private final String DB_NAME =
            Objects.requireNonNullElse(System.getenv("DB_NAME"), "insulin_hero_rebuild_db_test");


}
