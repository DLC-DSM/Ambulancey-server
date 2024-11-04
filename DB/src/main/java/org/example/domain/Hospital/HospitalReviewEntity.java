package org.example.domain.Hospital;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "hospital_review")
@Entity
public class HospitalReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinTable(name = "hospital_list")
    @JoinColumn(name = "hospital_id")
    private Long hospitalId;

    @JoinTable(name = "user")
    @JoinColumn(name = "user_key")
    private Long userId;
    private String review;
    private Double score;

}
