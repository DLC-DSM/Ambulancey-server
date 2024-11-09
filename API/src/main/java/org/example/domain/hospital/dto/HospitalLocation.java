package org.example.domain.hospital.dto;

import lombok.Data;
import org.example.annotation.LocalAddress;


public record HospitalLocation (
        double latitude,
        double longitude
){

}

