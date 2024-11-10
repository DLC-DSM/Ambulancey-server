package org.example.domain.Hospital;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.User.UserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hospital_manager")
public class HospitalManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_manager_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_key", nullable = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @Column(name = "hospital_role")
    private String hospitalRole;
}
