package com.mysite.sbb.user;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;

	@Column(unique = true)
	private String username;
	
	@Size(min=5)
	private String password;

	@Column(unique = true)
	private String email;

    @Column(nullable = false)
    private Boolean first; 

    // 기본 생성자 추가
    public SiteUser() {
        this.first = false; 
    }
	
}