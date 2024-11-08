package org.example.domain.disease.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.disease.DiseaseEntity;
import org.example.domain.disease.dto.Disease;
import org.example.repository.DiseaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    @Transactional
    public List<Disease> getDisease () {
        List<DiseaseEntity> diseaseEntities = diseaseRepository.findAll();

        List<String> signals = new ArrayList<>();
        List<Disease> diseaseList = new ArrayList<>();

        diseaseEntities.stream().forEach(entity -> {
            entity.getSignal();

            Disease disease = Disease.builder()
                    .name(entity.getName())
                    .description(entity.getInfo())
                    .diseaseSignal(signals)
                    .isHosp(entity.getNeedHospital())
                    .build();
            diseaseList.add(disease);
        });

        return diseaseList;
    }


    public Disease getDisease(Long id){
        List<String> signals = new ArrayList<>();


        DiseaseEntity entity = diseaseRepository.findById(id).orElseThrow(RuntimeException::new);
            entity.getSignal().forEach(e->{
                signals.add(e.getSignalName());
            });
            Disease disease = Disease.builder()
                    .name(entity.getName())
                    .description(entity.getInfo())
                    .isHosp(entity.getNeedHospital())
                    .diseaseSignal(signals)
                    .build();
            return disease;
    }

}
