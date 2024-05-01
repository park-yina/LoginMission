package com.mysite.profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public ResponseEntity<String> uploadProfile(MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("이미지가 첨부되지 않았습니다.", HttpStatus.BAD_REQUEST);
        }

        String imageFileName = generateUniqueFileName(file);
        Path imageFilePath = Paths.get(uploadFolder, imageFileName);

        try {
            Files.write(imageFilePath, file.getBytes());

            Profile profile = new Profile();
            profile.setImageUri(imageFileName);
            profileRepository.save(profile);

            return new ResponseEntity<>("이미지가 성공적으로 업로드되었습니다.", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("이미지 업로드 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateUniqueFileName(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        return uuid + "_" + file.getOriginalFilename();
    }
}
