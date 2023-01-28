package com.example.serverv6.dao.jdbcdao;

import com.example.serverv6.dao.dao.MealDao;
import com.example.serverv6.model.appentity.Meal;
import com.example.serverv6.model.appentity.MealInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcMealDao implements MealDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMealDao.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcMealDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Meal> getUserOneDayMeals(int userId) throws SQLException {

        List<Meal> dailyMeals = new ArrayList<>();

        String sql = "SELECT m.meal_id, serving_size_carbs, food_name, time_of_meal, date_of_meal " +
                "FROM meals m " +
                "JOIN meals_user_join mj ON m.meal_id = mj.meal_id " +
                "JOIN user_data ud ON ud.user_id = mj.user_id " +
                "WHERE ud.user_id = ? AND m.date_of_meal = date_trunc('day', current_date)";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            dailyMeals.add(mapRowToMealObject(rowSet));
        }
        if (dailyMeals.size() == 0) {
            throw new SQLException("Could not find any meals for this user.");
        } else {
            return dailyMeals;
        }


    }

    @Override
    public Meal getMostRecentUserMeal(int userId) throws SQLException {

        String sql = "SELECT m.meal_id, serving_size_carbs, food_name, time_of_meal, date_of_meal FROM meals m " +
                "JOIN meals_user_join mj ON mj.meal_id = m.meal_id " +
                "JOIN user_data ud ON ud.user_id = mj.user_id " +
                "WHERE ud.user_id = ? " +
                "ORDER BY m.meal_id DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()) {
            return mapRowToMealObject(rowSet);
        } else {
            throw new SQLException("No meals found for user " + userId);
        }
    }

    @Override
    public int createNewMeal(int userId, Meal meal) throws SQLException {

        try {

            String sql = "INSERT INTO meals (serving_size_carbs, food_name, time_of_meal, date_of_meal) " +
                    "VALUES (?, ?, ?, ?) RETURNING meal_id";

            int mealId = jdbcTemplate.queryForObject(sql, Integer.class, meal.getServingSizeCarbs(), meal.getFoodName(),
                    meal.getTimeOfMeal(), meal.getDateOfMeal());

            if (!(createMealUserJoinEntry(userId, mealId))) {
                throw new SQLException("Could not create meal join entry.");
            } else {
                return mealId;
            }

        } catch (SQLException e) {
            throw new SQLException("Could not create meal.");
        }

    }

    private boolean createMealUserJoinEntry(int mealId, int userId) throws SQLException {
        String sql = "INSERT INTO meal_user_data_join (meal_id, user_id) VALUES (?, ?);";
        int rowsAffected = jdbcTemplate.update(sql, mealId, userId);
        return rowsAffected == 1;
    }

    @Override
    public void saveMealInformation(int mealId, MealInformation mealInformation) throws SQLException {

        String sql = "INSERT INTO public.meal_information( " +
                "meal_id, glycemic_load, calories_g, total_fats_g, saturated_fats_g, trans_fats_g, fatty_acids_monosaturated_g, fatty_acids_polyunsaturated_g, " +
                "carbs_by_difference_g, total_carbs_g, fiber_g, sugars_g, added_sugars_g, protein_g, cholesterol_mg, sodium_mg, calcium_mg, magnesium_mg, potassium_mg, " +
                "iron_mg, zinc_mg, phosphorous_mg, vit_a_rae_micg, vit_c_mg, thiamin_mg, riboflavin_mg, niacin_mg, vit_b6_mg, folate_dfe_micg, folate_food_micg, folic_acid_micg, " +
                "vit_b12_micg, vit_d_micg, vit_e_micg, vit_k_micg, water_g) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        if (jdbcTemplate.update(sql, mealId, mealInformation.getGlycemicLoad(), mealInformation.getCalories(), mealInformation.getTotalFats(),
                mealInformation.getSaturatedFats(), mealInformation.getTransFats(), mealInformation.getFattyAcidsMonosaturated(), mealInformation.getFatyAcidsPolyUnsaturated(),
                mealInformation.getCarbsByDifference(), mealInformation.getTotalCarbs(), mealInformation.getFiber(), mealInformation.getSugars(), mealInformation.getAddedSugars(),
                mealInformation.getProtein(), mealInformation.getCholesterol(), mealInformation.getSodium(), mealInformation.getCalcium(), mealInformation.getMagnesium(),
                mealInformation.getPotassium(), mealInformation.getIron(), mealInformation.getZinc(), mealInformation.getPhosphorous(), mealInformation.getVitaminA(), mealInformation.getVitaminC(),
                mealInformation.getThamin(), mealInformation.getRiboflavin(), mealInformation.getNiacin(), mealInformation.getVitaminB6(), mealInformation.getFolate(), mealInformation.getFolateFromFood(),
                mealInformation.getFolicAcid(), mealInformation.getVitB12(), mealInformation.getVitD(), mealInformation.getVitE(), mealInformation.getVitK(), mealInformation.getWater()) != 1) {
            throw new SQLException("Could not create meal information");
        } else {
            logger.info("Created meal information object for meal: " + mealId + " at system time: " + System.currentTimeMillis());
        }

    }

    private Meal mapRowToMealObject(SqlRowSet rowSet) {
        Meal meal = new Meal();
        meal.setMealId(rowSet.getInt("meal_id"));
        meal.setServingSizeCarbs(rowSet.getDouble("serving_size_carbs"));
        meal.setFoodName(rowSet.getString("food_name"));
        meal.setTimeOfMeal(rowSet.getTime("time_of_meal").toLocalTime());
        meal.setDateOfMeal(rowSet.getDate("date_of_meal").toLocalDate());
        return meal;
    }

}
