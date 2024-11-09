package org.example.domain.review;

import jakarta.persistence.*;
import lombok.Data;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.User.UserEntity;

@Entity
@Table
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(name = "content_id", nullable = false)
    private String content;

    @Column(nullable = false)
    private Double star;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private HospitalEntity hospital;

}
