package org.example.domain.User;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_role")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_key")
    private UserEntity user;
    private String role;

}
