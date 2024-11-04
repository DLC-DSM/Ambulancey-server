package org.example.domain.hospital.dto;

import lombok.Data;

@Data
public class HospitalReviewsResponse {
    private String username;
    private String review;
    private Double star;
}
