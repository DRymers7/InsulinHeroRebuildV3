package com.example.serverv6.containertests;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUtilityClass {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = InsulinHeroPostgreSQLContainer.getInstance();

    // tests
}