package com.mysite.sbb.info;

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

        // 입력된 정보로 새로운 Info 생성
        String address = infoDto.getAddress();
        String nickname = infoDto.getNickname();
        String phoneNumber = infoDto.getPhoneNumber();
        System.out.println("singleFile : " + singleFile);
        
        // 2. 저장할 경로 가져오기
        String fileDir = environment.getProperty("file.dir");
        System.out.println("fileDir : " + fileDir);
        String root = fileDir + "/uploadFiles";

        File file = new File(root);

        // 만약 uploadFiles 폴더가 없으면 생성
        if (!file.exists()) {
            file.mkdirs();
        }

        // 업로드할 폴더 설정
        String originFileName = singleFile.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String ranFileName = UUID.randomUUID().toString() + ext;

        File changeFile = new File(root + "/" + ranFileName);

        // 파일 업로드
        try {
            singleFile.transferTo(changeFile);
            System.out.println("파일 업로드 성공");
        } catch (IllegalStateException | IOException e) {
            System.out.println("파일 업로드 실패");
            e.printStackTrace();
        }

        infoService.create(username, address, nickname, phoneNumber, ranFileName);
        infoService.changeFirst(username);

        return "redirect:/";
    }
}