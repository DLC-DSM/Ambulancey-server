package org.example.domain.hospital.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.Hospital.HospitalManagerEntity;
import org.example.domain.User.UserEntity;
import org.example.domain.User.UserRoleEntity;
import org.example.domain.hospital.dto.*;
import org.example.domain.hospital.exception.InvalidAddressException;
import org.example.domain.hospital.exception.NoHospitalException;
import org.example.domain.review.ReviewEntity;
import org.example.global.auth.user.exception.AlreadyRegisteredException;
import org.example.repository.*;

import org.springframework.data.crossstore.ChangeSetPersister;

import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final HospitalManagerRepository hospitalManagerRepository;
    private final ReviewRepository reviewRepository;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    public List<HospitalResponse> getLocationSearch(HospitalLocation location) {
        List<HospitalEntity> entity = hospitalRepository.findAll();
        entity.sort((a, b)->{
            double aDistance = calculateDistance(a.getLatitude(), a.getLongitude(), location.latitude(), location.longitude());
            double bDistance = calculateDistance(b.getLatitude(), b.getLongitude(), location.latitude(), location.longitude());
            return Double.compare(aDistance, bDistance);
        });
        List<HospitalResponse> hospitals = new ArrayList<>();

        entity.stream().forEach(e -> {
            HospitalResponse hospital = makeHospital(e);
            hospitals.add(hospital);
        });
        return hospitals;

    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // 지구의 반지름 (킬로미터 단위)
        double lat1Rad = Math.toRadians(lat1); // 위도 라디안으로 변환
        double lon1Rad = Math.toRadians(lon1); // 경도 라디안으로 변환
        double lat2Rad = Math.toRadians(lat2); // 위도 라디안으로 변환
        double lon2Rad = Math.toRadians(lon2); // 경도 라디안으로 변환

        double dLat = lat2Rad - lat1Rad; // 위도 차이
        double dLon = lon2Rad - lon1Rad; // 경도 차이

        // Haversine 공식 계산
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 킬로미터 단위의 거리 반환
    }

    @Transactional
    public HospitalResponse getHospital(Long hospitalId) {
        HospitalEntity hospitalEntity = hospitalRepository.findById(hospitalId).orElseThrow(NoHospitalException::new);
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
    public void HospitalUpdate (HospitalRequest hospital,Long id) {
        HospitalEntity hospitalEntity = hospitalRepository.findById(id).orElseThrow(NoHospitalException::new);
        updateHospitalEntity(hospital, hospitalEntity);
        hospitalRepository.save(hospitalEntity);
    }

    @Transactional
    public boolean application(HospitalRequest hospital,String username) throws Exception {
        if(hospitalRepository.findByHospitalName(hospital.getHospitalName()).isPresent()){

            throw AlreadyRegisteredException.EXCEPTION;
        }

        HospitalEntity hospitalEntity = makeHospitalEntity(hospital);

        log.info(hospitalEntity.getHospitalName());
        log.info(hospitalEntity.getAddress());
        log.info(hospitalEntity.getHospitalOpenDate().toString());
        hospitalRepository.save(hospitalEntity);
        log.info("save hospital is good");

        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        AtomicBoolean exist = new AtomicBoolean(false);
        user.getUserRoles().forEach(e -> {
                    if(e.getRole().matches("ROLE_HOSPITAL")){
                        exist.set(true);
                    }
        });

        if(!exist.get()) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUser(user);
            userRoleEntity.setRole("ROLE_HOSPITAL");
            userRoleRepository.save(userRoleEntity);
            user.getUserRoles().add(userRoleEntity);
            userRepository.save(user);

            HospitalManagerEntity hospitalManagerEntity = new HospitalManagerEntity();
            hospitalManagerEntity.setUser(user);
            hospitalManagerEntity.setHospital(hospitalEntity);
            hospitalManagerEntity.setHospitalRole("의료업무");
            hospitalManagerRepository.save(hospitalManagerEntity);
        }


        log.info("save is good");

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

        hospital.setId(hospitalEntity.getId());
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
        log.info(hospital.getHospitalName());
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

    public HospitalEntity updateHospitalEntity(HospitalRequest hospital, HospitalEntity hos) {
        log.info(hospital.getHospitalName());
        HospitalLocation coordinates = getCoordinates(hospital.getHospitalAddress());
        hos = HospitalEntity.builder()
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
        return hos;
    }
    public HospitalLocation getCoordinates(String address) {
        log.info(address);
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String url = "https://dapi.kakao.com/v2/local/search/address?size=1&query="+encodedAddress;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK 494978b66f1dba6d8e3fdfc5c402d430");
        RequestEntity<KakaoRequestBody> request = new RequestEntity<>
                (headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> response = restTemplate.exchange(request,String.class);
        String responseString = response.getBody();
        HospitalLocation coordinates;
        log.info(responseString);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseString);
            JsonNode documentsNode = rootNode.path("documents");

            if (documentsNode.isArray() && documentsNode.size() > 0) {
                JsonNode locationNode = documentsNode.get(0);
                double y = locationNode.path("y").asDouble();
                double x = locationNode.path("x").asDouble();
                coordinates = new HospitalLocation(y, x);
            } else {
                throw new InvalidAddressException();
            }
        } catch (Exception e) {
            log.error("Error parsing coordinates from response", e);
            throw new InvalidAddressException();
        }

        return coordinates;
    }
}
