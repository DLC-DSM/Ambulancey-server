package org.example.global.auth.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserReq extends User {
    private String email;
}
