package org.example.repository;

import org.example.domain.disease.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<DiseaseEntity, Long> {
}
