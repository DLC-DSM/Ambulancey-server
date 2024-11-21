package org.example.global.auth.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.hospital.dto.HospitalRequest;
import org.example.domain.hospital.service.HospitalService;
import org.example.global.auth.user.CustomUserDetails;
import org.example.global.auth.user.dto.HospitalUser;
import org.example.global.auth.user.dto.User;
import org.example.global.auth.user.dto.UserReq;
import org.example.global.auth.user.dto.UserResponse;
import org.example.global.auth.user.service.UserManagementService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final HospitalService hospitalService;

    @PostMapping("/register")
    public void  userRegister(@RequestBody UserReq user) {
        userManagementService.registerUser(user);
    }

    @PostMapping("/hospital_register")
    public ResponseEntity<Object> hospitalRegister(@RequestBody HospitalUser user) throws Exception {
//        if(!user.getAuthentication_key().equals("G7f6fG7GN6TFd5d67Hyvr5d576F67GNYVNytv76yuWZ33S4dfY8UkoPp")){
//            return ResponseEntity.badRequest().build();
//        }
        User userE = new User();
        userE.setUsername(user.getHospitalName());
        userE.setPassword(user.getPassword());
        //userE.setEmail(user.getEmail());
        log.info(userE.getUsername());

        HospitalRequest hospitalRequest = new HospitalRequest();

            hospitalRequest.setHospitalAddress(user.getHospitalAddress());
            hospitalRequest.setHospitalDescription(user.getHospitalDescription());
            hospitalRequest.setHospitalType(user.getHospitalType());
            hospitalRequest.setHospitalOpneDate(user.getHospitalOpneDate());
            hospitalRequest.setHospitalCloseDate(user.getHospitalCloseDate());
            hospitalRequest.setPhoneNumber(user.getPhoneNumber());
            hospitalRequest.setHospitalName(user.getHospitalName());

        userManagementService.registerHospital(userE);
        hospitalService.application(hospitalRequest,user.getHospitalName());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserResponse user = new UserResponse();
        user.setUsername(userDetails.getUsername());
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
