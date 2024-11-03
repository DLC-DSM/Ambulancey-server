package org.example.domain.disease;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DiseaseSignalEntity {
    @Id
    private String signalName;
}
