package org.example.domain.image.service;

import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.domain.Hospital.HospitalEntity;
import org.example.domain.Hospital.HospitalManagerEntity;
import org.example.domain.User.UserEntity;
import org.example.domain.hospital.exception.NoHospitalException;
import org.example.domain.image.ImageEntity;
import org.example.domain.image.ImageNotInsertException;
import org.example.domain.image.dto.ImageInsertRequest;
import org.example.domain.image.dto.ImageResponse;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final DiseaseRepository diseaseRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalManagerRepository hospitalManagerRepository;
    private final UserRepository userRepository;
    private final AwsFileService awsFileService;
    private final ImageRepository imageRepository;

    public Long hospitalIdFound(String username) throws Exception {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
        HospitalManagerEntity hospitalManagerEntity = hospitalManagerRepository.findByUser(user).orElseThrow(NoSuchElementException::new);
        return hospitalManagerEntity.getHospital().getId();
    }

    public void insertHospitalImage(ImageInsertRequest imageInsertRequest) throws NoHospitalException, IOException {
        HospitalEntity hospital = hospitalRepository.findById(imageInsertRequest.getHospitalId()).orElseThrow(NoHospitalException::new);
        if (imageInsertRequest.getImage().isEmpty()){
            throw new ImageNotInsertException();
        }
        String url = awsFileService.savePhoto(imageInsertRequest.getImage(), imageInsertRequest.getHospitalId());
        ImageEntity imageEntity = ImageEntity.builder()
                .hospitalEntity(hospital)
                .url(url).build();
        imageRepository.save(imageEntity);

    }

    public List<ImageResponse> getElementImages(HospitalEntity hospitalEntity){
        List<ImageEntity> imageEntities = imageRepository.findByHospitalEntity(hospitalEntity).orElseThrow(NoHospitalException::new);
        List<ImageResponse> imageResponses = imageEntities.stream().map((img)->{
            ImageResponse image = new ImageResponse();
            image.setId(img.getId());
            image.setUrl(img.getUrl());
            return image;
        }).toList();
        return imageResponses;
    }
}
