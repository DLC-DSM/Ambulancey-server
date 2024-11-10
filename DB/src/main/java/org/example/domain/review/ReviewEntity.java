package org.example.domain.review;

import jakarta.persistence.*;
import lombok.Data;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.User.UserEntity;

@Entity
@Table(name = "hospital_review")
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "review", nullable = false)
    private String content;

    @Column(nullable = false)
    private Double star;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_key")
    private UserEntity user;

    @ManyToOne(targetEntity = HospitalEntity.class)
    private HospitalEntity hospital;

}
