package com.example.demo.entity;



import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "User_Details")
@Data@AllArgsConstructor
@Builder
@ToString
@RequiredArgsConstructor
public class CustomUserDetails {
	
	@Id
	@Column(name = "user_id",unique = true)
	private String userId;
	
	
	@Column(name = "username",unique = true,nullable = false)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	private Double salary;
	
	private boolean isActive = true;
	
	
	@Column(name = "expiration_date")
	private String expirationDate;
	
	@Column(name = "company")
	private String company;
	
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "Authority_DB",joinColumns = @JoinColumn(name="USER_ID",referencedColumnName = "user_id"))
	@Column(name = "authorities")
	private Set<String> authorities;

}
