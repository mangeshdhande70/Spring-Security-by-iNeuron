package com.example.demo.dtos;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class UserDTO {

	private String userId;

	@NotNull(message = "Username is mandatory! please provide username")
	private String username;

	@NotNull(message = "Password is mandatory! please provide password")
	private String password;

	@NotNull(message = "Email is mandatory! please provide Email")
	private String email;

	private Double salary;

	private boolean isActive = true;

	private String expirationDate;

	private String company;

	private Set<String> authorities;

}
