package com.mysite.sbb;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.config.annotation.method.configuration.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
	            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
	        )
	        .csrf(csrf -> csrf
	            .ignoringRequestMatchers(
	                new AntPathRequestMatcher("/h2-console/**"),
	                new AntPathRequestMatcher("/login/social/processToken"),
	                new AntPathRequestMatcher("/error")
	            )
	        )
	        .headers(headers -> headers
	            .addHeaderWriter(new XFrameOptionsHeaderWriter(
	                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
	            ))
	        )
	        .formLogin(formLogin -> formLogin
	            .loginPage("/user/login")
	            .defaultSuccessUrl("/user/mypage/first")
	        )
	        .logout(logout -> logout
	            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
	            .logoutSuccessUrl("/")
	            .invalidateHttpSession(true)
	        );

	    return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount = 
                new FileInputStream("firebase.json");

            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
