package com.example.demo.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.dtos.UserDTO;
import com.example.demo.model.JwtResponse;
import com.example.demo.service.IUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	private long expirationTimeInMillis = 60*60*1000; 

	@Autowired
	private IUserDetailsService userService;

	@Value("${jwt.secrectKey}")
	private String secretKey;

	public JwtResponse generateToken(UserDetails userDetails) {

		LOGGER.info("Inside JwtUtils :: generateToken");

		UserDTO user = userService.getUserByUsername(userDetails.getUsername());
		String username = userDetails.getUsername();

		if (user == null) {
			LOGGER.info("User not exist with username " + username);
			return JwtResponse.builder().error("User not exist with username :: " + username).build();
		} else if (!user.isActive()) {
			LOGGER.info("Not Active User " + username);
			return JwtResponse.builder().error(username + " is not a active User").build();
		}

		LOGGER.info("Username :: " + userDetails.getUsername());
		LOGGER.info("User isActive :: " + user.isActive());
		
		Date now = new Date();
		Date expirationDate = new Date(now.getTime()+expirationTimeInMillis);

		Map<String, Object> claims = createAuthority(user);
		String token = Jwts.builder()
									.setClaims(claims)
									.setId(username)
									.setSubject(username)
									.setIssuedAt(now)
									.setExpiration(expirationDate)
									.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256).compact();

		return JwtResponse.builder().token(token).build();

	}

	public Map<String, Object> createAuthority(UserDTO user) {

		Map<String, Object> claims = new HashMap<>();

		try {
			claims.put("email", user.getEmail());
			claims.put("company", user.getCompany());
			claims.put("liceneceValidTill", user.getExpirationDate());
		} catch (Exception e) {
			LOGGER.error("error at JwtUtils :: createAuthority :: " + e);
		}
		return claims;
	}

	public String getUsernameFromToken(String token) {
		LOGGER.info("Inside JwtUtils :: getUsernameFromToken");
		return getClaimFromToken(token, Claims::getSubject);
	}

	// for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
		return Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromToken(String token) {
		final Date expirationDate = getClaimFromToken(token, Claims::getExpiration);
		return expirationDate;
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
