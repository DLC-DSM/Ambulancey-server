package org.example.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.hospital.dto.HospitalRequest;
import org.example.domain.review.dto.ReviewRequest;
import org.example.domain.review.service.ReviewService;
import org.example.global.auth.user.CustomUserDetails;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/insert")
    public ResponseEntity reviewInsert(@RequestBody ReviewRequest reviewRequest, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        reviewService.insert(reviewRequest, userDetails.getUsername());
        return ResponseEntity.ok(null);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> reviewUpdate(@RequestBody ReviewRequest reviewRequest, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        reviewService.update(reviewRequest,userDetails.getUsername());
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> reviewDelete(@RequestBody ReviewRequest reviewRequest, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        reviewService.delete(reviewRequest,userDetails.getUsername());
        return ResponseEntity.ok(null);
    }
}
