package com.example.serverv6.services.appservices;

import com.example.serverv6.dao.dao.BloodSugarDao;
import com.example.serverv6.model.appentity.BloodSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BloodSugarService {

    @Autowired
    private BloodSugarDao bloodSugarDao;

    public BloodSugarService() {}

    public Map<String, List<BloodSugar>> getBloodSugarHistory(int userId) throws SQLException {
        Map<String, List<BloodSugar>> bloodSugarData = new HashMap<>();
        bloodSugarData.put("thisWeek", getThisWeekBloodSugars(userId));
        bloodSugarData.put("previousWeek", getLastWeekBloodSugars(userId));
        return bloodSugarData;
    }

    private List<BloodSugar> getThisWeekBloodSugars(int userId) throws SQLException {
        return bloodSugarDao.getThisWeekBloodSugars(userId);
    }

    private List<BloodSugar> getLastWeekBloodSugars(int userId) throws SQLException {
        return bloodSugarDao.getPreviousWeekBloodSugars(userId);
    }
    
}
