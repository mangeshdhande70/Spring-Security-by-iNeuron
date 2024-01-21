package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CustController {
	
	@GetMapping(value = "/home")
	public String getHome() {
		return "Home";
	}
	
	@GetMapping(value = "/user")
	public String getUser() {
		return "Welcome User";
	}
	
	@GetMapping(value = "/admin")
	public String getAdmin() {
		return "Welcome Admin";
	}
	
	
	

}
