package org.example.repository;

import org.example.domain.Hospital.HospitalManagerEntity;
import org.example.domain.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface HospitalManagerRepository extends JpaRepository<HospitalManagerEntity, Long> {
    Optional<HospitalManagerEntity> findByUser(UserEntity user);
}
