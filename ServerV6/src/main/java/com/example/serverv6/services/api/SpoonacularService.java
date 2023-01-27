package com.example.serverv6.services.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class SpoonacularService {

    @Value("spoonacular.api.key")
    private String apiKey;


}
