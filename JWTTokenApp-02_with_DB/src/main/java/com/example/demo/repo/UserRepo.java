package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CustomUserDetails;


public interface UserRepo extends JpaRepository<CustomUserDetails, String> {
	
	public Optional<CustomUserDetails> findByUsername(String username);

}
