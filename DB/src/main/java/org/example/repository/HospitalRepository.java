package org.example.repository;

import org.example.domain.Hospital.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<HospitalEntity,Long> {

    @Query("SELECT h FROM HospitalEntity h WHERE h.address LIKE CONCAT('%',:locCity,'%') AND h.address LIKE CONCAT('%',:loc,'%')")
    Optional<List<HospitalEntity>> findByLocation(@Param("locCity") String locCity, @Param("loc") String loc);

    HospitalEntity findByAddress(String address);

    Optional<HospitalEntity> findByHospitalName(String hospitalName);


    // 송정동 -> 부산, 서울 둘다 존재.
}
