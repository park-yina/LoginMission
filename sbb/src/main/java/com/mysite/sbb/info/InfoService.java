package com.mysite.sbb.info;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InfoService {
    private final InfoRepository infoRepository;
    private final UserRepository userRepository;

    public Info create(String username, String address, String nickname, String phoneNumber, String filename) {
        Info info = new Info();
        info.setUsername(username);
        info.setAddress(address);
        info.setNickname(nickname);
        info.setPhoneNumber(phoneNumber);
        info.setPhoto(filename);
        this.infoRepository.save(info);
        return info;
    }

    public void changeFirst(String username) {
        Optional<SiteUser> siteUserOptional = this.userRepository.findByUsername(username);
        if (siteUserOptional.isPresent()) {
            SiteUser siteUser = siteUserOptional.get();
            siteUser.setFirst(true);
            this.userRepository.save(siteUser);
        }
    }
}
