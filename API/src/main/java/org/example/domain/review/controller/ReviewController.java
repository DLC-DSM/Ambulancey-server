package org.example.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.hospital.dto.HospitalRequest;
import org.example.domain.review.dto.ReviewRequest;
import org.example.domain.review.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/insert")
    public void reviewInsert(ReviewRequest reviewRequest) {
        reviewService.insert(reviewRequest);
    }

    @PutMapping("/update")
    public void reviewUpdate(ReviewRequest reviewRequest) {
        reviewService.update(reviewRequest);
    }

    @DeleteMapping("/insert")
    public void reviewDelete(ReviewRequest reviewRequest) {
        reviewService.delete(reviewRequest);
    }
}
