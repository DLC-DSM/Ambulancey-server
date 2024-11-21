package org.example.domain.image.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.web.multipart.MultipartFile;

public record ImageInsertRequest(
        Long hospitalId,
        MultipartFile Image
) {
}
