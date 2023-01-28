package com.example.serverv6.model.appentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodSugar {

    int bloodSugarId;
    int inputLevel;
    LocalTime timeOfMeasurement;
    LocalDate dateOfMeasurement;

}
