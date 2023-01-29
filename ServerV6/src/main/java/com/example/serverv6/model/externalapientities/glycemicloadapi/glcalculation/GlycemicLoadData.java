package com.example.serverv6.model.externalapientities.glycemicloadapi.glcalculation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GlycemicLoadData {

    @JsonProperty("totalGlycemicLoad")
    private double totalGlycemicLoad;

    @JsonProperty("ingredients")
    private Ingredient[] ingredients;

}
