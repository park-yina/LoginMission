package com.mysite.sbb.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/mypage/first")
public class InfoController {
    private final InfoService infoService;
    private final Environment environment;
    
    @PostMapping("/")
    public String submitInfo(InfoDTO infoDto, @RequestParam("file") MultipartFile singleFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        String username = infoDto.getUsername();
        if (!username.equals(loggedInUsername)) {
            return "form_errors"; 
        }
        
        // 파일 이름 생성
        String originalFileName = singleFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "-" + originalFileName;
        
        // 파일 저장
        try {
            String filePath = environment.getProperty("file.dir") + File.separator + fileName;
            File dest = new File(filePath);
            singleFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            // 파일 저장 중 오류가 발생하면 처리
            return "form_errors"; // 에러 페이지로 리다이렉트 또는 에러 메시지 표시
        }
        
        // 입력된 정보로 새로운 Info 생성
        String address = infoDto.getAddress();
        String nickname = infoDto.getNickname();
        String phoneNumber = infoDto.getPhoneNumber();
        
        infoService.create(username, address, nickname, phoneNumber, fileName);
        infoService.changeFirst(username);

        return "redirect:/";
    }
}
