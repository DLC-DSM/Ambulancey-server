package org.example.domain.disease;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class DiseaseEntity {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String info;
    @OneToMany
    private List<DiseaseSignalEntity> signal;
    private boolean needHospital;
}
