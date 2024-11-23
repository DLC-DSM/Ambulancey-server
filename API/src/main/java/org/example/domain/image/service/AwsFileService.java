package org.example.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AwsFileService{
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String savePhoto(MultipartFile file, Long memberId) throws IOException {
        String fileName = file.getOriginalFilename()+ memberId;
        File files = convertFile(file).orElseThrow(IOException::new);
        String uplodeImageUrl = upS3(files,fileName);
        removeFile(files);
        return uplodeImageUrl;
    }

    public void removeFile(File file) {
        if(file.delete()){
            log.info("File deleted successfully");
            return;
        }
        log.info("File could not be deleted!");
    }
    public String upS3(File file, String filename) {
        amazonS3Client.putObject(bucket,filename,file);
        return amazonS3Client.getUrl(bucket,filename).toString();
    }

    public Optional<File> convertFile(MultipartFile file) throws IOException {
        File file1 = new File(file.getOriginalFilename());
        if (file1.createNewFile()){
            try(FileOutputStream fos = new FileOutputStream(file1)) {
                fos.write(file.getBytes());
            }
            return Optional.of(file1);
        }
        return Optional.empty();
    }
}
