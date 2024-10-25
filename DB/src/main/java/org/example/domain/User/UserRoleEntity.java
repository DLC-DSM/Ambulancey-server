package org.example.domain.User;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private UserEntity user;
    private String role;

}
