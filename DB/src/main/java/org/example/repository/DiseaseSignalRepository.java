package org.example.repository;

import org.example.domain.disease.DiseaseSignalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiseaseSignalRepository extends JpaRepository<DiseaseSignalEntity,String> {
    List<DiseaseSignalEntity> findListById(String s);
}
