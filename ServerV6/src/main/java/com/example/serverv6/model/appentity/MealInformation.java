package com.example.serverv6.model.appentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealInformation {

    private int mealId;
    private double glycemicLoad;
    private double calories;
    private double totalFats;
    private double saturatedFats;
    private double transFats;
    private double fattyAcidsMonosaturated;
    private double fatyAcidsPolyUnsaturated;
    private double carbsByDifference;
    private double totalCarbs;
    private double fiber;
    private double sugars;
    private double addedSugars;
    private double protein;
    private double cholesterol;
    private double sodium;
    private double calcium;
    private double magnesium;
    private double potassium;
    private double iron;
    private double zinc;
    private double phosphorous;
    private double vitaminA;
    private double vitaminC;
    private double thamin;
    private double riboflavin;
    private double niacin;
    private double vitaminB6;
    private double folate;
    private double folateFromFood;
    private double folicAcid;

    private double vitB12;
    private double vitD;
    private double vitE;
    private double vitK;
    private double water;

}
