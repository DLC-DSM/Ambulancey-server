package org.example.domain.image.dto;

import org.springframework.web.multipart.MultipartFile;

public record DisImageInsertRequest(
        Long diseaseId,
        MultipartFile Image
) {
}
