package com.example.serverv6.services.api;

import com.example.serverv6.model.externalapientities.glycemicloadapi.recipeanalysis.IngredientAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@PropertySource("classpath:application.properties")
public class SpoonacularService {

    private static final Logger log = LoggerFactory.getLogger(SpoonacularService.class);

    @Value("spoonacular.api.key")
    private String apiKey;

    @Value("spoonacular.api.url")
    private String baseApiUrl;

    private final RestTemplate restTemplate;

    public SpoonacularService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<IngredientAnalysis> getQueryIngredients(String query) throws InterruptedException, ExecutionException {
        try {
            log.info("Making ingredient analysis request for: " + query + " at time " + System.currentTimeMillis());
            String url = baseApiUrl + "/recipes/queries/analyze" + "?apiKey=" + apiKey + "&q=" + query;
            IngredientAnalysis results = restTemplate.getForObject(url, IngredientAnalysis.class);
            return CompletableFuture.completedFuture(results);
        } catch (ResponseStatusException e) {
            log.info("Error completing ingredient analysis request for: " + query + " at time " + System.currentTimeMillis() + " reason: "
             + e.getMessage());
            throw new ExecutionException(e.getReason(), e);
        }
    }



}
