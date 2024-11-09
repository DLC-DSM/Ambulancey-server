package org.example.global.auth.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponse extends User {
    private List<? extends GrantedAuthority> authorities;
}
