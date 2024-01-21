package com.example.demo.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.service.UserServiceDetailsImpl;
import com.example.demo.utils.JwtAuthenticationEntryPoint;
import com.example.demo.utils.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		logger.info("Inside SecurityConfig :: securityFilterChain");

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
		.authorizeHttpRequests((request) ->

		    request
				.requestMatchers("/api/public/**","/ui/**").permitAll()
				.anyRequest().authenticated())
		
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))

//				.formLogin((form) -> form.defaultSuccessUrl("/ui/", true) 
//						.loginPage("/ui/login")
//						.loginProcessingUrl("/login") 
//						.failureUrl("/ui/login?error=true") 
//						.permitAll())

				.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
						.logoutSuccessUrl("/ui/login?logout")
						
						);
				

			

		return http.build();

	}

	@Autowired
	private UserServiceDetailsImpl userDetailsService;
	

	@Bean
	public AuthenticationProvider authenticationProvider() {
		logger.info("Inside SecurityConfig :: authenticationProvider");
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		logger.info("Inside SecurityConfig :: authenticationManager");
		return config.getAuthenticationManager();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		logger.info("Inside SecurityConfig :: corsConfigurationSource");
		
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:9999"));
		configuration.setAllowedMethods(List.of("GET", "POST"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
	

}
