package com.mysite.sbb.info;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Info {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String username;
	
	@Column(columnDefinition = "TEXT")
	private String address;
	private String nickname;
	private String phoneNumber;
	private String photo;
	
}
