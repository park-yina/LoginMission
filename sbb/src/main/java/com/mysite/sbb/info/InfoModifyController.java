package com.mysite.sbb.info;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/info")
public class InfoModifyController {

    private final InfoService infoService;
    private final Environment environment;

    @GetMapping()
    public String modify(Model model) {
        // 현재 로그인된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // 사용자의 정보를 가져옴
        Optional<Info> existingInfoOptional = infoService.getInfoByUsername(loggedInUsername);
        if (existingInfoOptional.isPresent()) {
            Info existingInfo = existingInfoOptional.get();
            InfoDTO infoDto = new InfoDTO();
            infoDto.setUsername(existingInfo.getUsername());
            infoDto.setAddress(existingInfo.getAddress());
            infoDto.setNickname(existingInfo.getNickname());
            infoDto.setPhoneNumber(existingInfo.getPhoneNumber());
            infoDto.setPhoto(existingInfo.getPhoto());
            // 모델에 InfoDTO 객체를 추가하여 수정 폼에 전달
            model.addAttribute("infoDto", infoDto);
            return "modify_mypage";
        } else {
            // 사용자 정보가 없는 경우에 대한 처리
            // 예를 들어 에러 페이지를 반환하거나 적절한 처리를 진행할 수 있음
            return "form_errors";
        }
    }

    @PostMapping()
    public String postModify(@Valid InfoDTO infoDto, BindingResult bindingResult, @RequestParam("file") MultipartFile singleFile) {
        if (bindingResult.hasErrors()) {
            return "modify_mypage"; // 오류가 있으면 다시 페이지로 이동
        } else {
            // 정상적인 처리
            // InfoDTO를 Info 엔티티로 변환하여 저장
            Info info = new Info();
            info.setUsername(infoDto.getUsername());
            info.setAddress(infoDto.getAddress());
            info.setNickname(infoDto.getNickname());
            info.setPhoneNumber(infoDto.getPhoneNumber());

            // 파일이 업로드 되었다면 새로운 파일로 사진 정보 업데이트
            if (!singleFile.isEmpty()) {
                // 새로운 파일 이름 생성
                String originalFileName = singleFile.getOriginalFilename();
                String ext = originalFileName.substring(originalFileName.lastIndexOf("."));

                // UUID를 사용하여 고유한 파일 이름 생성
                String ranFileName = UUID.randomUUID().toString() + ext;

                // 업로드된 파일 이름으로 사진 정보 업데이트
                info.setPhoto(ranFileName);

                // 파일 업로드
                String fileDir = environment.getProperty("file.dir");
                String root = fileDir;
                File file = new File(root);
          

                // 만약 uploadFiles 폴더가 없으면 생성
                if (!file.exists()) {
                    file.mkdirs();
                }

                File changeFile = new File(root + "/" + ranFileName);
                // 파일 업로드
                try {
                    singleFile.transferTo(changeFile);
                    System.out.println("파일 업로드 성공");
               

                    } catch (IllegalStateException | IOException e) {
                    System.out.println("파일 업로드 실패");
                    info.setPhoto("images.jpg");
                    e.printStackTrace();
                }
            } else {
                info.setPhoto("images.jpg");
            }
            
            String username=infoDto.getUsername();
            // Info 엔티티 저장
            String photo=infoDto.getPhoto();
            String nickname=infoDto.getNickname();
            String address=infoDto.getAddress();
            String phoneNumber=infoDto.getPhoneNumber();
            infoService.delete(infoDto.getUsername());
            infoDto.setUsername(username);
            infoDto.setAddress(address);
            infoDto.setNickname(nickname);
            infoDto.setPhoto(photo);
            infoDto.setPhoneNumber(phoneNumber);
            infoService.save(info);
            return "redirect:/"; // 성공 페이지로 이동
        }
    }

}
