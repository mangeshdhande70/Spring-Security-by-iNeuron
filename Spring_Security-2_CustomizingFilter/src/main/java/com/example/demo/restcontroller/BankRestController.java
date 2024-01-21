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
	
	@GetMapping(value = "/transfer")
	public String fundTransfer() {
		return "Fund Tranfer Successfully";
	}
	
	@GetMapping(value = "/balance")
	public String showBalance() {
		return "Your Balance is "+7896;
	}
	
	@GetMapping(value = "/about")
	public String aboutUs() {
		return "ICICI BANK";
	}
	
	@GetMapping(value = "/login")
	public String loginPage() {
		return "Login";
	}

}
