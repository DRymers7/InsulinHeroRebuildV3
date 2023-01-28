package com.example.serverv6.jdbcdaotests;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
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


    private static final String DB_USER =
            Objects.requireNonNullElse(System.getenv("DB_USER"), "postgres");

    @Value("integration.testing.password")
    private static final String DB_PASSWORD =
            Objects.requireNonNullElse(System.getenv("DB_PASSWORD"), "postgres1");

    private SingleConnectionDataSource adminDataSource;
    private JdbcTemplate adminJdbcTemplate;

    @PostConstruct
    public void setup() {
        if (System.getenv("DB_HOST") == null) {
            adminDataSource = new SingleConnectionDataSource();
            adminDataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
            adminDataSource.setUsername(DB_USER);
            adminDataSource.setPassword(DB_PASSWORD);
            adminJdbcTemplate = new JdbcTemplate(adminDataSource);
            adminJdbcTemplate.update("DROP DATABASE IF EXISTS \"" + DB_NAME + "\";");
            adminJdbcTemplate.update("CREATE DATABASE \"" + DB_NAME + "\";");
        }
    }

    private DataSource ds = null;

    @Bean
    public DataSource dataSource() throws SQLException {
        if (ds != null) return ds;

        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME));
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setAutoCommit(false);

        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("test-data.sql"));
        ds = dataSource;
        return ds;
    }

    @PreDestroy
    public void cleanup() throws SQLException {
        if (adminDataSource != null) {
            adminJdbcTemplate.update("DROP DATABASE \"" + DB_NAME + "\";");
            adminDataSource.getConnection().close();
            adminDataSource.destroy();
        }
    }

}
