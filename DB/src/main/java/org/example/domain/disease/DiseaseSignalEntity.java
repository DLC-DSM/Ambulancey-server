package org.example.domain.disease;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "symptom_list")
public class DiseaseSignalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinTable(name = "disease")
    @JoinColumn(name = "disease_id",referencedColumnName = "disease_id")
    private Long diseaseId;
    private String signalName;
}
