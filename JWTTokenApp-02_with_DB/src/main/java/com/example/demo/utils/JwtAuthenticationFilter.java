package com.example.demo.utils;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Autowired
	private JwtUtils jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		logger.info("Inside JwtAuthenticationFilter :: doFilterInternal ");
		String requestHeader = request.getHeader("Authorization");
		logger.info("Header :: {}", requestHeader);
		String username = null;
		String token = null;

		if (requestHeader != null && requestHeader.startsWith("Bearer")) {

			token = requestHeader.substring(7);
			try {
				username = this.jwtHelper.getUsernameFromToken(token);
				logger.info("Inside JwtAuthenticationFilter :: doFilterInternal Username :: "+username);
			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username  !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				logger.info("Given JWT Token has been expired!!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				logger.info("Some changed has done in token!! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			logger.info("Invalid Header Value !!");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			if (validateToken) {
				logger.info("Token Validated");
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				logger.info("Validation Failed !!");
			}

		}

		filterChain.doFilter(request, response);

	}

}