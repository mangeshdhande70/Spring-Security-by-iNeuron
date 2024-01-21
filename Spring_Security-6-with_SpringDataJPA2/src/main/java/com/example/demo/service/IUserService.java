package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.entity.Users;

public interface IUserService extends UserDetailsService {

	public String register(Users Users);
	
}
