package com.example.YoungTalens.controller;

import com.example.YoungTalens.dto.UserDto;
import com.example.YoungTalens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-accounts")
    public List<UserDto> createAccounts(@RequestBody List<String> emails) {
        return userService.createUsersFromEmails(emails);
    }
}
