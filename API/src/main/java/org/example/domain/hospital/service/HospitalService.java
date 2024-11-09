package org.example.domain.hospital.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.User.HospitalManagerEntity;
import org.example.domain.User.UserEntity;
import org.example.domain.User.UserRoleEntity;
import org.example.domain.hospital.dto.HospitalRequest;
import org.example.domain.hospital.dto.HospitalLocation;
import org.example.domain.hospital.exception.NoHospitalException;
import org.example.repository.HospitalManagerRepository;
import org.example.repository.HospitalRepository;
import org.example.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final HospitalManagerRepository hospitalManagerRepository;

    @Transactional
    public List<HospitalRequest> getLocationSearch(@Valid HospitalLocation location) {
        String[] local = location.location().split(" ");
        List<HospitalEntity> entity = hospitalRepository.findByLocation(local[0], local[2]).orElseThrow();
        List<HospitalRequest> hospitals = new ArrayList<>();

        entity.stream().forEach(e -> {
            HospitalRequest hospital = makeHospital(e);
            hospitals.add(hospital);
        });
        return hospitals;

    }

    @Transactional
    public HospitalRequest getHospital(String hospitalName) {
        HospitalEntity hospitalEntity = hospitalRepository.findByHospitalName(hospitalName).orElseThrow(NoHospitalException::new);
        HospitalRequest hospital = makeHospital(hospitalEntity);
        return hospital;

    }

    @Transactional
    public void deleteHospital(String hospitalName) {
        HospitalEntity hospitalEntity = hospitalRepository.findByHospitalName(hospitalName).orElseThrow(NoHospitalException::new);
        hospitalRepository.delete(hospitalEntity);
    }

    @Transactional
    public void hospital_manage(String username,String role) throws ChangeSetPersister.NotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(ChangeSetPersister.NotFoundException::new);
        HospitalManagerEntity hospitalManagerEntity = hospitalManagerRepository.findByUser(userEntity).orElseThrow(ChangeSetPersister.NotFoundException::new);

        hospitalManagerEntity.setHospitalRole(role);
        hospitalManagerRepository.save(hospitalManagerEntity);
    }

    @Transactional
    public void HospitalUpdate (HospitalRequest hospital) {
        if (hospitalRepository.findByHospitalName(hospital.getHospitalName()).isEmpty()) {
            throw new NoHospitalException();
        }
        HospitalEntity hospitalEntity = makeHospitalEntity(hospital);
        hospitalRepository.save(hospitalEntity);
    }

    @Transactional
    public boolean application(HospitalRequest hospital,String username) throws ChangeSetPersister.NotFoundException {

        if(hospitalRepository.findByHospitalName(hospital.getHospitalName()).isEmpty()){
            return false;
        }
        HospitalEntity hospitalEntity = makeHospitalEntity(hospital);

        hospitalRepository.save(hospitalEntity);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(ChangeSetPersister.NotFoundException::new);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(user);
        userRoleEntity.setRole("ROLE_HOSPITAL");
        user.getUserRoles().add(userRoleEntity);
        return true;
    }

    public HospitalRequest makeHospital(HospitalEntity hospitalEntity) {
        HospitalRequest hospital = new HospitalRequest();

        hospital.setHospitalName(hospitalEntity.getHospitalName());
        hospital.setHospitalOpneDate(hospitalEntity.getHospitalOpenDate());
        hospital.setHospitalCloseDate(hospitalEntity.getHospitalCloseDate());
        hospital.setHospitalDescription(hospitalEntity.getHospitalDescription());
        hospital.setHospitalType(hospitalEntity.getHospitalType());
        hospital.setHospitalAddress(hospitalEntity.getAddress());
        hospital.setPhoneNumber(hospitalEntity.getNumber());
        return hospital;
    }

    public HospitalEntity makeHospitalEntity(HospitalRequest hospital) {
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
