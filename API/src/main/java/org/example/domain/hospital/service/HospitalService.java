package org.example.domain.hospital.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.hospital.dto.Hospital;
import org.example.domain.hospital.dto.HospitalLocation;
import org.example.domain.hospital.exception.NoHospitalException;
import org.example.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    @Transactional
    public List<Hospital> getLocationSearch(@Valid HospitalLocation location) {
        String[] local = location.location().split(" ");
        List<HospitalEntity> entity = hospitalRepository.findByLocation(local[0], local[2]).orElseThrow();
        List<Hospital> hospitals = new ArrayList<>();

        entity.stream().forEach(e -> {
            Hospital hospital = makeHospital(e);
            hospitals.add(hospital);
        });
        return hospitals;

    }

    @Transactional
    public Hospital getHospital(String hospitalName) {
        HospitalEntity hospitalEntity = hospitalRepository.findByHospitalName(hospitalName).orElseThrow(NoHospitalException::new);
        Hospital hospital = makeHospital(hospitalEntity);
        return hospital;

    }

    @Transactional
    public void deleteHospital(String hospitalName) {
        HospitalEntity hospitalEntity = hospitalRepository.findByHospitalName(hospitalName).orElseThrow(NoHospitalException::new);
        hospitalRepository.delete(hospitalEntity);
    }

    @Transactional
    public void HospitalUpdate (Hospital hospital) {
        if (!hospitalRepository.canFindByhospitalName(hospital.getHospitalName())) {
            throw new NoHospitalException();
        }
        HospitalEntity hospitalEntity = makeHospitalEntity(hospital);
        hospitalRepository.save(hospitalEntity);
    }

    @Transactional
    public boolean application(Hospital hospital){

        if(hospitalRepository.canFindByhospitalName(hospital.getHospitalName())){
            return false;
        }
        HospitalEntity hospitalEntity = makeHospitalEntity(hospital);


        hospitalRepository.save(hospitalEntity);
        return true;
    }

    public Hospital makeHospital(HospitalEntity hospitalEntity) {
        Hospital hospital = new Hospital();

        hospital.setHospitalName(hospitalEntity.getHospitalName());
        hospital.setHospitalOpneDate(hospitalEntity.getHospitalOpenDate());
        hospital.setHospitalCloseDate(hospitalEntity.getHospitalCloseDate());
        hospital.setHospitalDescription(hospitalEntity.getHospitalDescription());
        hospital.setHospitalType(hospitalEntity.getHospitalType());
        hospital.setHospitalAddress(hospitalEntity.getAddress());
        hospital.setPhoneNumber(hospitalEntity.getNumber());
        return hospital;
    }

    public HospitalEntity makeHospitalEntity(Hospital hospital) {
        HospitalEntity hospitalEntity = new HospitalEntity();
        hospitalEntity.setHospitalName(hospital.getHospitalName());
        hospitalEntity.setHospitalOpenDate(hospital.getHospitalOpneDate());
        hospitalEntity.setHospitalCloseDate(hospital.getHospitalCloseDate());
        hospitalEntity.setHospitalDescription(hospital.getHospitalDescription());
        hospitalEntity.setHospitalType(hospital.getHospitalType());
        hospitalEntity.setAddress(hospital.getHospitalAddress());
        hospitalEntity.setNumber(hospital.getPhoneNumber());

        return hospitalEntity;
    }


}
