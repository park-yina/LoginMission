package com.mysite.sbb.user;
import org.hibernate.annotations.ColumnDefault;

import  jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserCreateForm {
	@Size(min = 3, max = 25)
	@NotEmpty(message = "사용자ID는 필수항목입니다.")
	private String username;
	
	@Size(min=5)
	@NotEmpty(message = "비밀번호는 5글자 이상으로 작성해야합니다.")
	private String password1;

	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String password2;

	@NotEmpty(message = "이메일은 필수항목입니다.")
	@Email
	private String email;
	
	@ColumnDefault("false")
	private Boolean first;  
}