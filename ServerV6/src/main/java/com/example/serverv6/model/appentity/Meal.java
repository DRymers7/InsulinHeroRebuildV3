package com.example.serverv6.model.appentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {

    private int mealId;
    private double servingSizeCarbs;
    private String foodName;
    private LocalTime timeOfMeal;
    private LocalDate dateOfMeal;

}
