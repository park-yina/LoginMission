package com.mysite.profile;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProfileDto {
    private MultipartFile file;
    private String address;
    private String number;
    private String nickname;

    public Profile toEntity(String imageUri) {
        Profile profile = new Profile();
        profile.setImageUri(imageUri);
        profile.setAddress(address);
        profile.setNumber(number);
        profile.setNickname(nickname);
        return profile;
    }
}
