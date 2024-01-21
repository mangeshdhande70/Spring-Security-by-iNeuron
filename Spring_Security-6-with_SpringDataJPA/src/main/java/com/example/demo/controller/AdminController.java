package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
	
	
	@GetMapping(value = "/get")
	public ResponseEntity<String> getAdmin() {
		return ResponseEntity.ok("Admin Page");
	}
	

}
