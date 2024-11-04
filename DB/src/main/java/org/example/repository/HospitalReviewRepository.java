package org.example.repository;

import org.example.domain.Hospital.HospitalReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalReviewRepository extends JpaRepository<HospitalReviewEntity, Long> {
}
