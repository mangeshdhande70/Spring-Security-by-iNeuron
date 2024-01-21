package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Users;
import com.example.demo.service.IUserService;

@Controller
@RequestMapping(value = "/ui")
public class AuthController {
	
	@Autowired
	private IUserService iUserService;
	
	
	@GetMapping(value = "/")
	public String getHomeage() {
		return "index";
	}
	
	
	@GetMapping(value = "/login")
	public String getLoginPage(@ModelAttribute("user") Users users ) {
		System.out.println("Inside UserController :: getLoginPage to get Login Page");
		return "custom_login";
	}
	
	
	
	@GetMapping(value = "/register")
	public String getRegistrationPage(@ModelAttribute("user") Users users ) {
		System.out.println("Inside UserController :: getRegistrationPage to get Registration Page");
		return "user_registration";
	}
	
	
	@PostMapping(value = "/register")
	public String registerUser(@ModelAttribute("user") Users users,Map<String, Object> map) {
		System.out.println("Inside UserController :: getRegistrationPage to Register User");
		String result = iUserService.register(users);
		map.put("message", result);
		return "user_registerd_success";	
	}
	
	
	@GetMapping("/denied")
	public String accessDenied() {
		return "access_denied";
	}
	
}
