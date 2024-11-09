package org.example.domain.hospital.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.hospital.dto.HospitalRequest;
import org.example.domain.hospital.dto.HospitalLocation;
import org.example.domain.hospital.dto.KakaoRequestBody;
import org.example.domain.hospital.exception.InvalidAddressException;
import org.example.domain.hospital.exception.NoHospitalException;
import org.example.repository.HospitalRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    @Transactional
    public List<HospitalRequest> getLocationSearch(@Valid HospitalLocation location) {
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
    public void HospitalUpdate (HospitalRequest hospital) {
        if (hospitalRepository.findByHospitalName(hospital.getHospitalName()).isEmpty()) {
            throw new NoHospitalException();
        }
        HospitalEntity hospitalEntity = makeHospitalEntity(hospital);
        hospitalRepository.save(hospitalEntity);
    }

    @Transactional
    public boolean application(HospitalRequest hospital){

        if(hospitalRepository.findByHospitalName(hospital.getHospitalName()).isEmpty()){
            return false;
        }
        HospitalEntity hospitalEntity = makeHospitalEntity(hospital);


        hospitalRepository.save(hospitalEntity);
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
        double[] coordinates = getCoordinates(hospital.getHospitalAddress());
        HospitalEntity hospitalEntity = HospitalEntity.builder()
                .hospitalName(hospital.getHospitalName())
                .hospitalOpenDate(hospital.getHospitalOpneDate())
                .hospitalCloseDate(hospital.getHospitalCloseDate())
                .hospitalDescription(hospital.getHospitalDescription())
                .hospitalType(hospital.getHospitalType())
                .address(hospital.getHospitalAddress())
                .number(hospital.getPhoneNumber())
                .latitude(coordinates[0])
                .longitude(coordinates[1])
                .build();
        return hospitalEntity;
    }
    public double[] getCoordinates(String address) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK 494978b66f1dba6d8e3fdfc5c402d430");
        RequestEntity<KakaoRequestBody> request = new RequestEntity<>
                (headers, HttpMethod.GET, URI.create("https://dapi.kakao.com/v2/local/search/address?size=1&query="+address));
        ResponseEntity<String> response = restTemplate.exchange(request,String.class);
        String responseString = response.getBody();
        double[] coordinates = new double[2];
        try {
            String y = responseString.substring(responseString.indexOf("y\":\"")+1, responseString.indexOf("\",\"x\""));
            String x = responseString.substring(responseString.indexOf("x\":\"")+1, responseString.indexOf("\",\"address_type"));
            coordinates[0] = Double.parseDouble(y);
            coordinates[1] = Double.parseDouble(x);

        } catch (NullPointerException e) {
            throw new InvalidAddressException();
        }
        return coordinates;
    }
}
