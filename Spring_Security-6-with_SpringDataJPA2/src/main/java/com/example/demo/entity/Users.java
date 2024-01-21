package com.example.demo.entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "MD_USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Users{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;
	
	
	@Column(length = 100,unique = true,nullable = false,columnDefinition = "VARCHAR(255)")
	private String username;
	
	@Column(length = 100,nullable = false,columnDefinition = "VARCHAR(255)")
	private String password;
	
	
	private String email;
	
	private boolean active = true;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "MD_SECURITY_ROLES",joinColumns = @JoinColumn(name="USER_ID",referencedColumnName = "uid"))
	@Column(name = "role")
	private Set<String> roles;

}
