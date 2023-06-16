package com.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users_details")
public class UserDetails {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "User name is not entered, Enter User name")
	private String name;
	
	@NotEmpty(message = "User Phone number is not entered, Enter User phone number")
	private String phoneNumber;
	
	@NotEmpty(message = "User Occupation is not entered, Enter User Occupation")
	private String occupation;
	
	@NotNull(message = "User age is empty, enter user age ")
	private int age;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location location;

	
	}

	
	
	
	
	


