package com.example.demo.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repo.UserRepo;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("Inside UserServiceImpl :: loadUserByUsername for authenticate the user");

		Optional<Users> optional = userRepo.findByUsername(username);
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("User not found! please try with correct username or password");
		}

		Users users = optional.get();
		User user = new User(users.getUsername(), users.getPassword(),
				users.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles)).collect(Collectors.toSet()));

		return user;
	}

	@Override
	public String register(Users user) {
		
		if (userRepo.existsByUsername(user.getUsername())) {
			throw new DuplicateKeyException("User is already Registered by this username! please try with different username");	
		}
		
		user.setPassword(encoder.encode(user.getPassword()));
		Users users = userRepo.save(user);
		return users != null ? "User Registered Successfully" : "User Registration Failed";
	}

}
