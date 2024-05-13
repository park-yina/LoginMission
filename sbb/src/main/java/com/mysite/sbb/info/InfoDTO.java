package com.mysite.sbb.info;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoDTO {
	@NotEmpty(message="아이디를 입력해주세요")
    private String username;
	@NotEmpty(message="주소를 입력해주세요")
    private String address;
	@NotEmpty(message="닉네임을 입력해주세요")
    private String nickname;
	@NotEmpty(message="전화번호를 입력해주세요")
    private String phoneNumber;
    private String photo;
}
