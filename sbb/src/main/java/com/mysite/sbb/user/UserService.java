package com.mysite.sbb.user;

import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	}
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser=this.userRepository.findByUsername(username);
		if(siteUser.isPresent()) {
			return siteUser.get();
		}
		else {
			throw new DataNotFoundException("siteuser Not Found");
		}

	}
	public Boolean isFirst(String username) {
	    Optional<SiteUser> siteUserOptional = this.userRepository.findByUsername(username);
	    if (siteUserOptional.isPresent()) {
	        SiteUser siteUser = siteUserOptional.get();
	        return siteUser.getFirst();
	    } else {
	        return null;
	    }
	}

}