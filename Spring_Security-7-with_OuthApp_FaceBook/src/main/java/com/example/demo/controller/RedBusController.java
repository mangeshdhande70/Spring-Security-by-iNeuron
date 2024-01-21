package com.example.demo.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedBusController {
	
	
	@GetMapping(value = "/home")
	public String showHome() {
		return "Hello, Welocme to Home Page of RedBus.com";
	}

	
	
	@GetMapping(value = "/after")
	public String afterLogin() {
		return "Hello, Successfully logged into RedBus.com";
	}
	
	
	@GetMapping(value = "/user")
	public Authentication showUserDetails(Principal principal) {
		
		System.out.println("Logged in Details :: "+ principal.getName());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication;
	}


}
