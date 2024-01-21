package com.example.demo.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bank")
public class BankRestController {
	
	@GetMapping(value = "/")
	public String welcomePage() {
		return "Welcome to ICIC BANK";
		}

}
