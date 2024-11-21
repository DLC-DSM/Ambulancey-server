package org.example.domain.hospital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.example.annotation.LocalAddress;
import org.example.annotation.PhoneNumber;

import java.time.LocalTime;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)

public class HospitalUpdateRequest {


    private Long id;

    private String hospitalName;

    private String hospitalDescription;

    @LocalAddress
    private String hospitalAddress;

    private String hospitalType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hospitalOpneDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hospitalCloseDate;

    @PhoneNumber
    private String phoneNumber;

}
