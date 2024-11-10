package org.example.domain.hospital.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.hospital.dto.HospitalLocation;
import org.example.domain.hospital.dto.HospitalRequest;
import org.example.domain.hospital.dto.HospitalResponse;
import org.example.domain.hospital.service.HospitalService;
import org.example.global.auth.user.CustomUserDetails;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class hospitalController{

    private final HospitalService hospitalService;

    @PostMapping("/application")
    public ResponseEntity hospitalApplication(
            @Valid
            @RequestBody
            HospitalRequest hospital,
            Authentication authentication
    ) throws Exception {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("병원 application");
        log.info(hospital.getHospitalName());
        boolean ok = hospitalService.application(hospital,customUserDetails.getUsername());
        log.info("save");
        return ok ? ResponseEntity.ok(null) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/update")
    public void hospitalUpdate(HospitalRequest hospital){
        hospitalService.HospitalUpdate(hospital);
    }

    @DeleteMapping("/resign")
    public void hospitalResign(String hospitalName){
        hospitalService.deleteHospital(hospitalName);
    }

    @GetMapping("/list")
    public ResponseEntity<? extends Object> getHospitalList(@RequestBody HospitalLocation location){
        log.info(String.valueOf(location.longitude()));
        List<HospitalResponse> list = hospitalService.getLocationSearch(location);
        return new ResponseEntity<List>(list, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/info")
    public ResponseEntity<? extends Object> getHospitalInfo(@NotNull Long id){

        HospitalResponse hospitalResponse = hospitalService.getHospital(id);

        return ResponseEntity.ok(hospitalResponse);
    }




}
