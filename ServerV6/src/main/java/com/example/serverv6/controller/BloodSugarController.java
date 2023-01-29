package com.example.serverv6.controller;

import com.example.serverv6.dao.dao.BloodSugarDao;
import com.example.serverv6.dao.dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class BloodSugarController {

    private BloodSugarDao bloodSugarDao;
    private UserDao userDao;

    public BloodSugarController(BloodSugarDao bloodSugarDao, UserDao userDao) {
        this.bloodSugarDao = bloodSugarDao;
        this.userDao = userDao;
    }

}
