package org.example.repository;

import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.User.UserEntity;
import org.example.domain.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Integer> {
    List<ReviewEntity> findByHospital(HospitalEntity hospital);
    void deleteByHospital(HospitalEntity hospital);
    Optional<ReviewEntity> findById(Long id);
}