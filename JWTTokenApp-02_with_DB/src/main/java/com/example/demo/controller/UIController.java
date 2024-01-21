package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dtos.UserDTO;
import com.example.demo.entity.CustomUserDetails;
import com.example.demo.model.JwtRequest;
import com.example.demo.service.IUserDetailsService;

@Controller
@RequestMapping(value = "/ui")
public class UIController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	

	@Autowired
	@Qualifier("userServiceDetailsImpl")
	private IUserDetailsService userService;

	@GetMapping(value = "/")
	public String getHomeage() {
		return "index";
	}

	@GetMapping(value = "/login")
	public String getLoginPage(@ModelAttribute("user") JwtRequest jwtRequest) {
		System.out.println("Inside UIController :: getLoginPage to get Login Page");
		return "custom_login";
	}

	@GetMapping("/denied")
	public String accessDenied() {
		return "access_denied";
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<String> createUser(@ModelAttribute("user") UserDTO user) {
		logger.info("Inside UserRestController :: createUser to create new user");
		String userId = userService.createUser(user);
		return new ResponseEntity<String>("User Created with id :: " + userId, HttpStatus.CREATED);

	}
	
	@GetMapping(value = "/register")
	public String getRegistrationPage(@ModelAttribute("user") CustomUserDetails users ) {
		System.out.println("Inside UserController :: getRegistrationPage to get Registration Page");
		return "user_registration";
	}
	

}
