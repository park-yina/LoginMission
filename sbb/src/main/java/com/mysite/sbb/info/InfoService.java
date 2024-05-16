package com.mysite.sbb.info;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

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
    public Optional<Info> getInfoByUsername(String username) {
        return infoRepository.findByUsername(username);
    }
    public Info save(Info info) {
        return infoRepository.save(info);
    }
    public Info delete(String username) {
        // 사용자 이름에 해당하는 정보를 조회
        Optional<Info> infoOptional = infoRepository.findByUsername(username);
        
        // 정보가 존재하는지 확인하고 삭제
        if (infoOptional.isPresent()) {
            Info info = infoOptional.get();
            infoRepository.delete(info);
            return info;
        } else {
            // 정보가 존재하지 않으면 null 또는 예외 처리
            return null; // 또는 예외 처리를 할 수 있습니다.
        }
    }

}

