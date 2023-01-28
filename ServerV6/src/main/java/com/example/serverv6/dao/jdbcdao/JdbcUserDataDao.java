package com.example.serverv6.dao.jdbcdao;

import com.example.serverv6.dao.dao.UserDataDao;
import com.example.serverv6.model.appentity.UserData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;

public class JdbcUserDataDao implements UserDataDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDataDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserData getUserData(int userId) throws SQLException {

        String sql = "SELECT user_id, a1c, target_low, target_high, fasting_glucose, diabetes_type, prediabetic, user_age, " +
                "weight_kg, height_meters, daily_carb_goal, daily_calorie_goal, avg_glycemic_index_goal, emergency_contact_1, " +
                "emergency_contact_2, date_last_updated, time_last_updated " +
                "FROM user_data " +
                "WHERE user_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()) {
            return mapRowToUserData(rowSet);
        } else {
            throw new SQLException("Could not find applicable user.");
        }

    }

    private UserData mapRowToUserData(SqlRowSet rowSet) {
        UserData userData = new UserData();
        userData.setUserId(rowSet.getInt("user_id"));
        userData.setA1c(rowSet.getDouble("a1c"));
        userData.setTargetLow(rowSet.getInt("target_low"));
        userData.setTargetHigh(rowSet.getInt("target_high"));
        userData.setFastingGlucose(rowSet.getInt("fasting_glucose"));
        userData.setDiabetesType(rowSet.getInt("diabetes_type"));
        userData.setPreDiabetic(rowSet.getBoolean("prediabetic"));
        userData.setUserAge(rowSet.getInt("user_age"));
        userData.setWeightKg(rowSet.getInt("weight_kg"));
        userData.setHeightM(rowSet.getInt("height_meters"));
        userData.setDailyCarbGoal(rowSet.getInt("daily_carb_goal"));
        userData.setDailyCalorieGoal(rowSet.getInt("daily_calorie_goal"));
        userData.setGlycemicIndexGoal(rowSet.getDouble("avg_glycemic_index_goal"));
        userData.setEmergencyContact1(rowSet.getString("emergency_contact_1"));
        userData.setEmergencyContact2(rowSet.getString("emergency_contact_2"));
        userData.setDateLastUpdated(rowSet.getDate("date_last_updated").toLocalDate());
        userData.setTimeLastUpdated(rowSet.getTime("time_last_updated").toLocalTime());
        return userData;
    }
}
