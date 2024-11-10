package org.example.domain.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospitalReviewsResponse {
    private String username;
    private String review;
    private Double star;
    private Long review_id;
}
