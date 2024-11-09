package org.example.global.auth.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.global.auth.user.CustomUserDetails;
import org.example.global.auth.user.dto.User;
import org.example.global.auth.user.dto.UserResponse;
import org.example.global.auth.user.service.UserManagementService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping("/register")
    public void  userRegister(@RequestBody User user) {
        userManagementService.registerUser(user);
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserResponse user = new UserResponse();
        user.setEmail(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setAuthorities(userDetails.authorities());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/out")
    public String userOut(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userManagementService.deleteUser(userDetails.getUsername());
        return "redirect:/user/logout";
    }

    @PatchMapping("/update")
    public String  userUpdate(User user,Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userManagementService.updateUsername(user,userDetails.username());
        return "redirect:/user/logout";
    }
}
