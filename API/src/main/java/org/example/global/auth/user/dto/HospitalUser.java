package org.example.global.auth.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.domain.hospital.dto.HospitalRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class HospitalUser extends HospitalRequest {
    private String username;
    private String password;
    private String authentication_key; // 원장 인증.
}
