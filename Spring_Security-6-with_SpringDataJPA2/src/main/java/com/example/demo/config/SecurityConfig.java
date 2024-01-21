package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Inside SecurityConfig :: configAuthentication");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices)
			throws Exception {
		System.out.println("Inside SecurityConfig :: securityFilterChain");
		http.authorizeHttpRequests((request) -> request.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN").requestMatchers("/ui/**").permitAll()
				.anyRequest().authenticated())

				.formLogin((form) -> form
						.defaultSuccessUrl("/ui/", true) // home page url
						.loginPage("/ui/login")       // for GET mode request to launch for
						.loginProcessingUrl("/login") // for POST request to submit process the page
						.failureUrl("/ui/login?error=true") // Authentication failed
						.permitAll())

				.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
						.logoutSuccessUrl("/ui/login?logout")

				)

				.exceptionHandling((ex) -> ex.accessDeniedPage("/ui/denied"))

				.sessionManagement((session) -> session.maximumSessions(2).maxSessionsPreventsLogin(true))
				.rememberMe((remember) -> remember.rememberMeServices(rememberMeServices));

		;

		return http.build();

	}

	@Bean
	RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
		RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
		TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("mykey", userDetailsService,
				encodingAlgorithm);
		rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
		return rememberMe;
	}

}
