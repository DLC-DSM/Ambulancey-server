package org.example.domain.disease.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Disease {
    private String name;
    private String description;
    private List<String> diseaseSignal;
    private boolean isHosp;
}
