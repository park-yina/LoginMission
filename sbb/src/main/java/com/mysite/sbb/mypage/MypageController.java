package com.mysite.sbb.mypage;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.mysite.sbb.info.Info;
import com.mysite.sbb.info.InfoDTO;
import com.mysite.sbb.info.InfoRepository;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class MypageController {

    private final InfoRepository infoRepository;

    public MypageController(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    @GetMapping("/user/mypage")
    public ModelAndView myPage() {
        ModelAndView modelAndView = new ModelAndView("user_mypage"); // ModelAndView를 생성합니다.

        // 현재 로그인된 사용자의 이름 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // 현재 로그인된 사용자의 정보 가져오기
        Optional<Info> infoOptional = infoRepository.findByUsername(loggedInUsername);
        InfoDTO userInfo = infoOptional.map(info -> {
            InfoDTO infoDTO = new InfoDTO();
            infoDTO.setUsername(info.getUsername());
            infoDTO.setAddress(info.getAddress());
            infoDTO.setNickname(info.getNickname());
            infoDTO.setPhoneNumber(info.getPhoneNumber());
            infoDTO.setPhoto(info.getPhoto());
            return infoDTO;
        }).orElse(null);
        
        // ModelAndView에 userInfo를 추가합니다.
        modelAndView.addObject("userInfo", userInfo);

        return modelAndView;
    }
}

