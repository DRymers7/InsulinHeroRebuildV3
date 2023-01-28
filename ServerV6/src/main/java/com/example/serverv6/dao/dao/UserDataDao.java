package com.example.serverv6.dao.dao;

import com.example.serverv6.model.appentity.UserData;

import java.sql.SQLException;

public interface UserDataDao {

    UserData getUserData(int userId) throws SQLException;

}
