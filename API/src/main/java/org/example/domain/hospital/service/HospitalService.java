package org.example.domain.hospital.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.hospital.dto.Hospital;
import org.example.domain.hospital.dto.HospitalLocation;
import org.example.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public List<Hospital> getLocationSearch(@Valid HospitalLocation location) {
        String[] local = location.location().split(" ");

    }

    public boolean application(Hospital hospital){

    }
}
