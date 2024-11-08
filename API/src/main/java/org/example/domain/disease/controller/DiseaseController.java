package org.example.domain.disease.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.disease.dto.Disease;
import org.example.domain.disease.service.DiseaseService;
import org.example.domain.hospital.service.HospitalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DiseaseController {

    private final DiseaseService diseaseService;

    @GetMapping("/disease")
    public List<Disease> getDiseaseList(){
        return diseaseService.getDisease();
    }

    @GetMapping("/diease/{id}")
    public Disease getDisease(@PathVariable Long id){
        return diseaseService.getDisease(id);
    }
}
