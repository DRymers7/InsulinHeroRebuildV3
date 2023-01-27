package com.example.serverv6.services.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class EdamamService {

    @Value("edamam.application.id")
    private String applicationId;

    @Value("edamam.application.key")
    private String applicationKey;


}
