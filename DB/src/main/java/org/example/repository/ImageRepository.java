package org.example.repository;

import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.image.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity,Long> {

    Optional<List<ImageEntity>> findByHospitalEntity(HospitalEntity hospitalEntity);
}
