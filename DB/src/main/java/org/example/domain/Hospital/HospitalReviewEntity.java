package org.example.domain.Hospital;

import jakarta.persistence.*;
import lombok.Data;
import org.example.domain.User.UserEntity;

@Data
@Table(name = "hospital_review")
@Entity
public class HospitalReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "hospital_hospital_id",referencedColumnName = "hospital_id")
    private HospitalEntity hospitalId;

    @ManyToOne
    @JoinColumn(name = "user_key")
    private UserEntity userId;

    private String review;
    private Double score;

}
