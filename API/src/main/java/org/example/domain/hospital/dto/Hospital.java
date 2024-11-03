package org.example.domain.hospital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.example.annotation.LocalAddress;
import org.example.annotation.PhoneNumber;

import java.util.Date;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Hospital {

    private String hospitalName;

    private String hospitalDescription;

    @LocalAddress
    private String hospitalAddress;

    private String hospitalType;

    private Date hospitalOpneDate;

    private Date hospitalCloseDate;

    @PhoneNumber
    private String phoneNumber;
}
