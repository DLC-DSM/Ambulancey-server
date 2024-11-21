package org.example.domain.image.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.image.dto.ImageInsertRequest;
import org.example.domain.image.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image")
    public ResponseEntity<Object> image(@RequestPart ImageInsertRequest insertRequest) {
        imageService.insertImage(insertRequest);
        return ResponseEntity.ok().build();
    }
}
