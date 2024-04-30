package com.mysite.profile;

import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Profile {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = true)
	    private String imageUri;

	    @Column(nullable = true)
	    private String address;
	    @Column(nullable = true)
	    private String number;
	    @Column(nullable=true)
	    private String nickname;
	    @OneToOne
	    @JoinColumn(name = "username", referencedColumnName = "username")
	    private SiteUser siteUser;
}
