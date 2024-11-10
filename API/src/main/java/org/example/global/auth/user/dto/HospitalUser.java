package org.example.global.auth.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.domain.hospital.dto.HospitalRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class HospitalUser extends HospitalRequest {
    private String username;
    private String password;
}
