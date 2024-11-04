package org.example.domain.hospital.dto;

import lombok.Data;
import org.example.annotation.LocalAddress;
import org.example.annotation.PhoneNumber;

import java.util.Date;
import java.util.List;

@Data
public class HospitalResponse {
    private String hospitalName;

    private String hospitalDescription;

    @LocalAddress
    private String hospitalAddress;

    private String hospitalType;

    private Date hospitalOpneDate;

    private Date hospitalCloseDate;

    @PhoneNumber
    private String phoneNumber;

    private List<HospitalReviewsResponse> hospitalReviews;

}
