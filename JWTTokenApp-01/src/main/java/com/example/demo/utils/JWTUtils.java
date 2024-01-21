package com.example.demo.utils;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.util.Base64Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTUtils {

	public static String generatToken(String id, String subject, String secret) {

		return Jwts.builder().setId(id).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)))
				.signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(secret.getBytes())).compact();

	}

	public static Claims getClaims(String secret, String token) {
		return Jwts.parser().setSigningKey(Base64.getEncoder().encode(secret.getBytes())).parseClaimsJws(token)
				.getBody();

	}

	public static Date getExpirationDate(String secretKey, String token) {
		return getClaims(secretKey, token).getExpiration();
	}

	public static boolean validateToken(String secretKey, String token) {

		try {
			Claims claims = getClaims(secretKey, token);
			if (claims != null && new Date().before(getExpirationDate(token, token)))
				return true;
		} catch (SignatureException ex) {
			System.out.println("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty.");
		}
		return false;
	}

}
