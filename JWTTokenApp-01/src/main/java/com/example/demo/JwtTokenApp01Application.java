package com.example.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.utils.JWTUtils;

import io.jsonwebtoken.Claims;

//@SpringBootApplication
public class JwtTokenApp01Application {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(JwtTokenApp01Application.class, args);
		
		String secret = "MangeshDhandeNirliNagpurInfosysSpringBootFramework";
		
		String secret2 = "MangeshDhandeNirliNagpurInfosysSpringBootFrameworkAkash";
		
		String token = JWTUtils.generatToken("1234", "UPI_PIN", secret);
		System.out.println(token);
		
		
		Claims claims = JWTUtils.getClaims(secret, token);
		System.out.println("Subject :: "+claims.getSubject());
		System.out.println("ID :: "+claims.getId());
		System.out.println("IssuerDate :: "+claims.getIssuedAt());
		System.out.println("Expiration :: "+claims.getExpiration());
		
		
		System.in.read();
		boolean validateToken = JWTUtils.validateToken(secret, token);
		System.out.println(validateToken);
		
		
	}

}
