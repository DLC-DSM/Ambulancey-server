package org.example.domain.hospital.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.hospital.dto.Hospital;
import org.example.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class hospitalService {
    private final HospitalRepository hospitalRepository;

    public List<Hospital> getLocationSearch(String location) {

    }
}
