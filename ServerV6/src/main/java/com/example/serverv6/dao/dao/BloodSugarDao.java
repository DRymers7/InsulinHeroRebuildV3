package com.example.serverv6.dao.dao;

import com.example.serverv6.model.appentity.BloodSugar;

import java.sql.SQLException;
import java.util.List;

public interface BloodSugarDao {

    BloodSugar getBloodSugar(int userId) throws SQLException;
    BloodSugar createBloodSugar(int userId, BloodSugar bloodSugar) throws SQLException;
    List<BloodSugar> getThisWeekBloodSugars(int userId) throws SQLException;
    List<BloodSugar> getPreviousWeekBloodSugars(int userId) throws SQLException;
}
