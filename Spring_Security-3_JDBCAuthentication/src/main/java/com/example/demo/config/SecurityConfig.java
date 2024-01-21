package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Autowired
	private DataSource dataSource;
	
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
		System.out.println("Inside SecurityConfig :: configAuthentication");
		auth
		.jdbcAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())
		.dataSource(dataSource)
		.usersByUsernameQuery("Select username,password,active from cust where username = ?")
		.authoritiesByUsernameQuery("select username,roles from authorities where username = ?");
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		System.out.println("Inside SecurityConfig :: securityFilterChain");
		
		http.authorizeHttpRequests((request)->
		                request.requestMatchers("/api/home/").permitAll()
		                		.requestMatchers("/api/admin/").hasRole("ADMIN")
		                        .requestMatchers("/api/user/").hasAnyRole("USER","ADMIN")
		                        .anyRequest().authenticated()).formLogin();
		
		return http.build();
	}

}
