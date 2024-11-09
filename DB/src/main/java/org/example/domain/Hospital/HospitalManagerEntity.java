package org.example.domain.Hospital;

import jakarta.persistence.*;
import lombok.Data;
import org.example.domain.User.UserEntity;

@Data
@Entity
@Table(name = "hospital_manager")
public class HospitalManagerEntity {

    @EmbeddedId
    private HospitalManagerId id;

    @ManyToOne(optional = false)
    @MapsId("userKey")  // 복합 키의 userKey 필드를 매핑
    @JoinColumn(name = "user_key", nullable = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    @MapsId("hospitalId")  // 복합 키의 hospitalId 필드를 매핑
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @Column(name = "hospital_role")
    private String hospitalRole;
}
