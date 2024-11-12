package org.example.domain.hospital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.example.annotation.LocalAddress;
import org.example.annotation.PhoneNumber;

import java.time.LocalTime;
import java.util.Date;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)

public class HospitalRequest {

    private Long id;

    @JsonProperty("hospital_name")
    private String hospitalName;

    @JsonProperty("description")
    private String hospitalDescription;

    //@LocalAddress
    @JsonProperty("address")
    private String hospitalAddress;

    @JsonProperty("type")
    private String hospitalType;

    @JsonProperty("open_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hospitalOpneDate;

    @JsonProperty("close_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hospitalCloseDate;

    //@PhoneNumber
    @JsonProperty("phone")
    private String phoneNumber;

}
