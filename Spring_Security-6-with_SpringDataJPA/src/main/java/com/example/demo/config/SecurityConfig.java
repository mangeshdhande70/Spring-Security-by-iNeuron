package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Inside SecurityConfig :: configAuthentication");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("Inside SecurityConfig :: securityFilterChain");
		http.authorizeHttpRequests((request)->
		 									request
		 									.requestMatchers("/admin/**").hasAuthority("ADMIN")
		 									.requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
		 									.requestMatchers("/ui/**").permitAll()
		 									.anyRequest().authenticated()).formLogin().and().exceptionHandling().accessDeniedPage("/ui/denied");
		
		
		return http.build();
		
	}

}
