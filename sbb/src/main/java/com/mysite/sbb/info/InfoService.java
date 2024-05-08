package com.mysite.sbb.info;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InfoService {
	private final InfoRepository infoRepository;
	public Info create(String username, String address, String nickname, String phoneNumber,String filename) {
		Info info = new Info();
		info.setUsername(username);
		info.setAddress(address);
		info.setNickname(nickname);
		info.setPhoneNumber(phoneNumber);
		info.setPhoto(filename);
		this.infoRepository.save(info);
		return info;
	}
}
