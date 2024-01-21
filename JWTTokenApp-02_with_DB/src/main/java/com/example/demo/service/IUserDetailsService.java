package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.dtos.UserDTO;

public interface IUserDetailsService extends UserDetailsService {
	
	public String createUser(UserDTO userDTO);
	
	public UserDTO getUserByUserId(String userId);
	
	public UserDTO getUserByUsername(String username);

}
