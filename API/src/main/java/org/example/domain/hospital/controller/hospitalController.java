package org.example.domain.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.hospital.dto.Hospital;
import org.example.domain.hospital.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class hospitalController{

    private final HospitalService hospitalService;

    @PostMapping("/application")
    public void hospitalApplication(Hospital hospital){
        hospitalService.application(hospital);
    }

    @PutMapping("/update")
    public void hospitalUpdate(){

    }

    @DeleteMapping("/resign")
    public void hospitalResign(){

    }

    @GetMapping("/list")
    public ResponseEntity<? extends Object> getHospitalList(){
        return null;
    }

    @GetMapping("/info")
    public ResponseEntity<? extends Object> getHospitalInfo(){
        return null;
    }




}
