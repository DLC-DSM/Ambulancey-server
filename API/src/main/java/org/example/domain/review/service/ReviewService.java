package org.example.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.hospital.exception.NoHospitalException;
import org.example.domain.review.ReviewEntity;
import org.example.domain.review.dto.ReviewRequest;
import org.example.domain.review.exception.ReviewNotFoundException;
import org.example.repository.HospitalRepository;
import org.example.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final HospitalRepository hospitalRepository;

    public void insert(ReviewRequest reviewRequest) {
        ReviewEntity review = new ReviewEntity();
        review.setHospital(hospitalRepository.findById(reviewRequest.hospital_id()).orElseThrow(NoHospitalException::new));
        review.setContent(reviewRequest.content());
        review.setStar(reviewRequest.star());
        reviewRepository.save(review);
    }

    public void update(ReviewRequest reviewRequest) {
        ReviewEntity review = reviewRepository.findById(reviewRequest.review_id()).orElseThrow(ReviewNotFoundException::new);
        review.setContent(reviewRequest.content());
        review.setStar(reviewRequest.star());
        reviewRepository.save(review);
    }

    public void delete(ReviewRequest reviewRequest) {
        ReviewEntity review = reviewRepository.findById(reviewRequest.review_id()).orElseThrow(ReviewNotFoundException::new);
        reviewRepository.delete(review);
    }
}
