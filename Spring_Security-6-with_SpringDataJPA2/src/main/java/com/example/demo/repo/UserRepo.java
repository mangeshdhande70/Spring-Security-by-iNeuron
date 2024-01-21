package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Users;


public interface UserRepo extends JpaRepository<Users, Integer> {
	
	public Optional<Users> findByUsername(String username);
	
	public boolean existsByUsername(String username);

}
