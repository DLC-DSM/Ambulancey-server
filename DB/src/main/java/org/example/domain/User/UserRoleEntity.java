package org.example.domain.User;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class UserRoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private UserEntity user;
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
