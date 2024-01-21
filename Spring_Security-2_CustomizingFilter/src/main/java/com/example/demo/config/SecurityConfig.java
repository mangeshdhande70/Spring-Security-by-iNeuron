package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	static {
		System.out.println("SecurityConfig Class Loaded");
	}
	
	public SecurityConfig() {
		System.out.println("SecurityConfig Object Created");
	}
	
	

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		System.out.println("Inside SecurityConfig :: securityFilterChain");

		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/bank/", "/bank/about", "/login").permitAll()
				.anyRequest().authenticated()).formLogin();


		return http.build();
	}

	

}
