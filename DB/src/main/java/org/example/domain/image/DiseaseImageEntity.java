package org.example.domain.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.disease.DiseaseEntity;

@Entity
@Table(name = "disease_image_data")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DiseaseImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "disease_id")
    private DiseaseEntity diseaseEntity;

    private String url;
}
