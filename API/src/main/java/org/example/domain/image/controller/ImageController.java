package org.example.domain.image.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.image.dto.ImageInsertRequest;
import org.example.domain.image.service.ImageService;
import org.example.global.auth.user.CustomUserDetails;
import org.example.global.auth.user.service.UserManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final UserManagementService userManagementService;

    @PostMapping("/hos_image")
    public ResponseEntity<Object> image(ImageInsertRequest insertRequest, Authentication authentication) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long hos = imageService.hospitalIdFound(userDetails.username());
        insertRequest.setHospitalId(hos);
        imageService.insertHospitalImage(insertRequest);
        return ResponseEntity.ok().build();
    }

}
