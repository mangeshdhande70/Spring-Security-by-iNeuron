package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.UserDTO;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.service.IUserDetailsService;
import com.example.demo.utils.JwtUtils;

@RestController
@RequestMapping(path = "/api/public")
public class AuthApi {

	
private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;
	

	@Autowired
	@Qualifier("userServiceDetailsImpl")
	private IUserDetailsService userService;
	
	

	@GetMapping(value = "/hello")
	public String getHello(@RequestParam String name) {
		String n = name;
		return "Hello , "+name;
	}
	
	

	@PostMapping(value = "/create")
	public String createUser(@RequestBody UserDTO user) {
		logger.info("Inside AuthApi :: createUser to create new user");
		String userId = userService.createUser(user);
		return "User Created with id :: " + userId;

	}
	

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception 
	{
		logger.info("Inside AuthApi :: createAuthenticationToken to create token");
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userService
				.loadUserByUsername(authenticationRequest.getUsername());
		
//		final UserEntity userDetails = userService.getUserByUserId(authenticationRequest.getUsername());		
		final JwtResponse jwtResponse = jwtUtils.generateToken(userDetails);

		return ResponseEntity.ok(jwtResponse);
	}
	
	

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	
}
