package com.example.demo.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.UserDTO;
import com.example.demo.entity.CustomUserDetails;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repo.UserRepo;
import com.example.demo.utils.AppUtils;

@Service("userServiceDetailsImpl")
public class UserServiceDetailsImpl implements IUserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AppUtils appUtils;

	@Autowired
	private PasswordEncoder encoder;
	
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info("Inside UserServiceDetailsImpl :: loadUserByUsername");

		Optional<CustomUserDetails> optional = userRepo.findByUsername(username);
		if (optional.isEmpty()) {
			throw new UserNotFoundException("User is not present with this Username please provide correct Username");
		}

		CustomUserDetails userFromDb = optional.get();
		Set<SimpleGrantedAuthority> authorities = userFromDb.getAuthorities().stream()
				.map(auth -> new SimpleGrantedAuthority(auth)).collect(Collectors.toSet());
		
		logger.info("Username :: "+username);
		logger.info(username);
		User user = new User(userFromDb.getUsername(), userFromDb.getPassword(), authorities);
//		User user = (User) User.builder().username(userFromDb.getUsername()).password(userFromDb.getPassword()).authorities(authorities).build();

		return user;
	}
	

	@Override
	public String createUser(UserDTO userDTO) {

		userDTO.setPassword(encoder.encode(userDTO.getPassword()));
		userDTO.setUserId(UUID.randomUUID().toString());
	
			
		CustomUserDetails entity = CustomUserDetails.builder()
		          .userId(userDTO.getUserId())
		          .username(userDTO.getUsername())
		          .email(userDTO.getEmail())
		          .password(userDTO.getPassword())
		          .company(userDTO.getCompany())
		          .authorities(userDTO.getAuthorities())
		          .expirationDate(userDTO.getExpirationDate())
		          .isActive(userDTO.isActive())
		          .salary(userDTO.getSalary()).build();
		
		CustomUserDetails user = userRepo.save(entity);
		return user.getUserId();
	}

	@Override
	public UserDTO getUserByUserId(String userId) {
		
		CustomUserDetails userDetails = userRepo.findById(userId).orElseThrow(()->
		        new UsernameNotFoundException(""));
		 userDetails.setPassword("");
		
		return  appUtils.copyToObject(userDetails, UserDTO.class);
	}


	@Override
	public UserDTO getUserByUsername(String username) {
		
		CustomUserDetails userDetails = userRepo.findByUsername(username).orElseThrow(()->
        new UsernameNotFoundException(""));		
		
		UserDTO userDTO = UserDTO.builder().userId(userDetails.getUserId())
		                 .authorities(userDetails.getAuthorities())
		                 .company(userDetails.getCompany())
		                 .email(userDetails.getEmail())
		                 .expirationDate(userDetails.getExpirationDate())
		                 .isActive(userDetails.isActive())
		                 .salary(userDetails.getSalary())
		                 .username(userDetails.getUsername()).build();
		
		
	    return userDTO;
	}

}
