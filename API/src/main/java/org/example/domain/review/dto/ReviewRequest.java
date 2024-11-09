package org.example.domain.review.dto;

public record ReviewRequest(
        Long hospital_id,
        Long review_id,
        String content,
        Double star
){

}
