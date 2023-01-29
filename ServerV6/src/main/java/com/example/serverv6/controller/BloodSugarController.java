package com.example.serverv6.controller;

import com.example.serverv6.dao.dao.BloodSugarDao;
import com.example.serverv6.dao.dao.UserDao;
import com.example.serverv6.model.appentity.BloodSugar;
import com.example.serverv6.services.appservices.BloodSugarService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class BloodSugarController {

    private BloodSugarDao bloodSugarDao;
    private UserDao userDao;
    private BloodSugarService bloodSugarService;

    public BloodSugarController(BloodSugarDao bloodSugarDao, UserDao userDao, BloodSugarService bloodSugarService) {
        this.bloodSugarDao = bloodSugarDao;
        this.userDao = userDao;
        this.bloodSugarService = bloodSugarService;
    }

    @GetMapping("/bloodsugars")
    public BloodSugar getBloodSugar(Principal principal) {
        try {
            int userId = userDao.findIdByUsername(principal.getName());
            return bloodSugarDao.getBloodSugar(userId);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bloodsugars")
    public BloodSugar createBloodSugar(@RequestBody BloodSugar bloodSugar, Principal principal) {
        try {
            int userId = userDao.findIdByUsername(principal.getName());
            return bloodSugarDao.createBloodSugar(userId, bloodSugar);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bloodsugars/history")
    public Map<String, List<BloodSugar>> getPreviousWeekBloodSugars(Principal principal) {
        try {
            int userId = userDao.findIdByUsername(principal.getName());
            return bloodSugarService.getBloodSugarHistory(userId);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
