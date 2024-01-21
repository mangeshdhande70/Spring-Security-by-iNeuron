package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.UserDTO;
import com.example.demo.service.IUserDetailsService;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("userServiceDetailsImpl")
	private IUserDetailsService userService;
	
	
	@GetMapping(value = "/get/{userId}")
	public ResponseEntity<UserDTO> getUserByUserId(@PathVariable String userId) {

		logger.info("Inside UserRestController :: getUserByUserId to get User Information");
		
		UserDTO user = userService.getUserByUserId(userId);
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	
	

	
	

}
