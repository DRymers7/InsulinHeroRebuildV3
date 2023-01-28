package com.example.serverv6.dao.dao;

import com.example.serverv6.model.appentity.Meal;
import com.example.serverv6.model.appentity.MealInformation;

import java.sql.SQLException;
import java.util.List;

public interface MealDao {

    List<Meal> getUserOneDayMeals(int userId) throws SQLException;
    Meal getMostRecentUserMeal(int userId) throws SQLException;
    int createNewMeal(int userId, Meal meal) throws SQLException;
    void saveMealInformation(int mealId, MealInformation mealInformation) throws SQLException;
}
