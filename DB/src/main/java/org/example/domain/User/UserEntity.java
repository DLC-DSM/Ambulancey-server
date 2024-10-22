package org.example.domain.User;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table
@Data
public class UserEntity {
    @Id
    @GeneratedValue
    private String username;
    private String password;
    @OneToMany
    List<UserRoleEntity> userRoles;
}
