package com.example.serverv6.model.appentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    private int userId;
    private double a1c;
    private int targetLow;
    private int targetHigh;
    private int fastingGlucose;
    private int diabetesType;
    private boolean preDiabetic;
    private int userAge;
    private int weightKg;
    private int heightM;
    private int dailyCarbGoal;
    private int dailyCalorieGoal;
    private double glycemicIndexGoal;
    private String emergencyContact1;
    private String emergencyContact2;
    private LocalDate dateLastUpdated;
    private LocalTime timeLastUpdated;
}
