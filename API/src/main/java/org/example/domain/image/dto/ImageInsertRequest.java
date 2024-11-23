package org.example.domain.image.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageInsertRequest {
    Long hospitalId;
    MultipartFile Image;
}
