package org.example.domain.hospital.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.hospital.dto.HospitalLocation;
import org.example.domain.hospital.dto.HospitalRequest;
import org.example.domain.hospital.service.HospitalService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class hospitalController{

    private final HospitalService hospitalService;

    @PostMapping("/application")
    public void hospitalApplication(HospitalRequest hospital){
        hospitalService.application(hospital);
    }

    @PutMapping("/update")
    public void hospitalUpdate(HospitalRequest hospital){
        hospitalService.HospitalUpdate(hospital);
    }

    @DeleteMapping("/resign")
    public void hospitalResign(){
        
    }

    @GetMapping("/list")
    public ResponseEntity<? extends Object> getHospitalList(@Valid HospitalLocation location){
        List<HospitalRequest> list = hospitalService.getLocationSearch(location);
        return new ResponseEntity<List>(list, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/info")
    public ResponseEntity<? extends Object> getHospitalInfo(){
        return null;
    }




}
