package org.example.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.hospital.exception.NoHospitalException;
import org.example.domain.image.ImageNotInsertException;
import org.example.domain.image.dto.ImageInsertRequest;
import org.example.repository.HospitalRepository;
import org.example.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final HospitalRepository hospitalRepository;
    private final ImageRepository imageRepository;

    public void insertImage(ImageInsertRequest imageInsertRequest) throws NoHospitalException {
        hospitalRepository.findById(imageInsertRequest.hospitalId()).orElseThrow(NoHospitalException::new);
        if (imageInsertRequest.Image().isEmpty()){
            throw new ImageNotInsertException();
        }

    }
}
