package org.example.domain.hospital.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.Hospital.HospitalManagerEntity;
import org.example.domain.User.UserEntity;
import org.example.domain.User.UserRoleEntity;
import org.example.domain.hospital.dto.*;
import org.example.domain.hospital.exception.InvalidAddressException;
import org.example.domain.hospital.exception.NoHospitalException;
import org.example.domain.review.ReviewEntity;
import org.example.repository.HospitalManagerRepository;
import org.example.repository.HospitalRepository;

import org.example.repository.ReviewRepository;
import org.example.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;

import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final HospitalManagerRepository hospitalManagerRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public List<HospitalResponse> getLocationSearch(@Valid HospitalLocation location) {
        List<HospitalEntity> entity = hospitalRepository.findAll();
        entity.sort((a, b)->{
            double aLatitude = a.getLatitude()-location.latitude()*a.getLatitude()-location.latitude();
            double aLongitude = a.getLongitude()-location.longitude()*a.getLongitude()-location.longitude();
            double aDistance = aLatitude+aLongitude;
            double bLatitude = b.getLatitude()-location.latitude()*b.getLatitude()-location.latitude();
            double bLongitude = b.getLongitude()-location.longitude()*b.getLongitude()-location.longitude();
            double bDistance = bLatitude+bLongitude;
            return Double.compare(aDistance, bDistance);
        });
        List<HospitalResponse> hospitals = new ArrayList<>();

        entity.stream().forEach(e -> {
            HospitalResponse hospital = makeHospital(e);
            hospitals.add(hospital);
        });
        return hospitals;

    }

    @Transactional
    public HospitalResponse getHospital(String hospitalName) {
        HospitalEntity hospitalEntity = hospitalRepository.findByHospitalName(hospitalName).orElseThrow(NoHospitalException::new);
        return makeHospital(hospitalEntity);

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

    public HospitalResponse makeHospital(HospitalEntity hospitalEntity) {
        List<ReviewEntity> reviewEntitys = reviewRepository.findByHospital(hospitalEntity);
        List<HospitalReviewsResponse> reviews = reviewEntitys.stream().map(
                a->new HospitalReviewsResponse(
                        a.getUser().getUsername(),
                        a.getContent(),
                        a.getStar(),
                        a.getId()
                )
        ).toList();

        HospitalResponse hospital = new HospitalResponse();

        hospital.setHospitalName(hospitalEntity.getHospitalName());
        hospital.setHospitalOpneDate(hospitalEntity.getHospitalOpenDate());
        hospital.setHospitalCloseDate(hospitalEntity.getHospitalCloseDate());
        hospital.setHospitalDescription(hospitalEntity.getHospitalDescription());
        hospital.setHospitalType(hospitalEntity.getHospitalType());
        hospital.setHospitalAddress(hospitalEntity.getAddress());
        hospital.setPhoneNumber(hospitalEntity.getNumber());
        hospital.setHospitalReviews(reviews);
        return hospital;
    }

    public HospitalEntity makeHospitalEntity(HospitalRequest hospital) {
        HospitalLocation coordinates = getCoordinates(hospital.getHospitalAddress());
        HospitalEntity hospitalEntity = HospitalEntity.builder()
                .hospitalName(hospital.getHospitalName())
                .hospitalOpenDate(hospital.getHospitalOpneDate())
                .hospitalCloseDate(hospital.getHospitalCloseDate())
                .hospitalDescription(hospital.getHospitalDescription())
                .hospitalType(hospital.getHospitalType())
                .address(hospital.getHospitalAddress())
                .number(hospital.getPhoneNumber())
                .latitude(coordinates.latitude())
                .longitude(coordinates.longitude())
                .build();
        return hospitalEntity;
    }
    public HospitalLocation getCoordinates(String address) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK 494978b66f1dba6d8e3fdfc5c402d430");
        RequestEntity<KakaoRequestBody> request = new RequestEntity<>
                (headers, HttpMethod.GET, URI.create("https://dapi.kakao.com/v2/local/search/address?size=1&query="+address));
        ResponseEntity<String> response = restTemplate.exchange(request,String.class);
        String responseString = response.getBody();
        HospitalLocation coordinates;
        try {
            String y = responseString.substring(responseString.indexOf("y\":\"")+1, responseString.indexOf("\",\"x\""));
            String x = responseString.substring(responseString.indexOf("x\":\"")+1, responseString.indexOf("\",\"address_type"));
            coordinates = new HospitalLocation(Double.parseDouble(y), Double.parseDouble(x));

        } catch (NullPointerException e) {
            throw new InvalidAddressException();
        }
        return coordinates;
    }
}
