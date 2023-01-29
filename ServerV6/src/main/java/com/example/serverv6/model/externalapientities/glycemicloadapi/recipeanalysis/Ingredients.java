package com.example.serverv6.model.externalapientities.glycemicloadapi.recipeanalysis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Ingredients {

    @JsonProperty("name")
    private String name;
    @JsonProperty("include")
    private boolean include;

}
