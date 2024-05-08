package com.mysite.sbb.info;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/mypage/first")
public class InfoController {

    private final InfoService infoService;

    @PostMapping("/")
    public String submitInfo(InfoDTO infoDto) {
        // 현재 로그인된 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // 입력된 정보와 로그인된 사용자의 정보 비교
        String username = infoDto.getUsername();
        if (!username.equals(loggedInUsername)) {
            // 유저네임이 일치하지 않는 경우 처리
            // 예를 들어, 에러 페이지로 리다이렉트 또는 에러 메시지 표시 등
            return "form_errors"; 
        }

        // 입력된 정보로 새로운 Info 생성
        String address = infoDto.getAddress();
        String nickname = infoDto.getNickname();
        String phoneNumber = infoDto.getPhoneNumber();
        infoService.create(username, address, nickname, phoneNumber);

        return "redirect:/";
    }
}

