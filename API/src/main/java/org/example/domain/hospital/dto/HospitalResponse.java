package org.example.domain.hospital.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Id;
import lombok.Data;
import org.example.annotation.LocalAddress;
import org.example.annotation.PhoneNumber;
import org.example.domain.image.dto.ImageResponse;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class HospitalResponse {

    private Long id;

    private String hospitalName;

    private String hospitalDescription;

    @LocalAddress
    private String hospitalAddress;

    private String hospitalType;

    private LocalTime hospitalOpneDate;

    private LocalTime hospitalCloseDate;

    @PhoneNumber
    private String phoneNumber;

    private List<HospitalReviewsResponse> hospitalReviews;
    private List<ImageResponse> images;

}
