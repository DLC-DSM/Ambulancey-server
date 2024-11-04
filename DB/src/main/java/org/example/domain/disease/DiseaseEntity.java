package org.example.domain.disease;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "disease")
public class DiseaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String info;
    @OneToMany
    private List<DiseaseSignalEntity> signal;

    private Boolean needHospital;
}
