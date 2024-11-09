package org.example.global.auth.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.global.auth.user.dto.User;
import org.example.global.auth.user.service.UserManagementService;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping("/register")
    public void  userRegister(@RequestBody User user) {
        userManagementService.registerUser(user);
    }
}
