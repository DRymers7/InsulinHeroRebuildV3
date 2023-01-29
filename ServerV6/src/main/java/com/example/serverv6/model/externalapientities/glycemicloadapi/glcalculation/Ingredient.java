package com.example.serverv6.model.externalapientities.glycemicloadapi.glcalculation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Ingredient {

    @JsonProperty("original")
    private String original;
    @JsonProperty("glycemicIndex")
    private double glycemicIndex;
    @JsonProperty("glycemicLoad")
    private double glycemicLoad;

}
