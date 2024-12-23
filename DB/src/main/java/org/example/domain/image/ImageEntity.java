package org.example.domain.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;

@Entity
@Table(name = "image_data")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospitalEntity;

    @Column(name = "url")
    private String url;
}
