package org.example.domain.User;

import jakarta.persistence.*;
import lombok.Data;
import org.example.annotation.LocalAddress;
import org.example.annotation.PhoneNumber;
import org.example.domain.Hospital.HospitalEntity;

import java.util.Date;

@Data
@Entity
@Table(name = "hospital_manager")
public class HospitalManagerEntity {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_key", nullable = false)
    private UserEntity user;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @Column(name = "hospital_role")
    private String hospitalRole;


}
