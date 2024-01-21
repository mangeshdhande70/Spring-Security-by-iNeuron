package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	
	@GetMapping(value = "/get")
	public String getAdmin() {
		return "user";
	}

}
