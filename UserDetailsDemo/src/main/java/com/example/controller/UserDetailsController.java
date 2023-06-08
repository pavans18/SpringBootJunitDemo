package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.UserDetails;
import com.example.exception.ResourceNotFoundException;

import com.example.service.UserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserDetailsController {
	
	@Autowired
	UserDetailsService service;
	
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDetails>> getAllUsers(){
		List<UserDetails> users = service.getAllUsers();
		if(users.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(users));
	}
	
	@PostMapping(path="/saveUser", produces= {"application/xml"}, consumes = {"application/xml"})
	public ResponseEntity<UserDetails> createUser(@Valid @RequestBody UserDetails user){
		
		UserDetails createdUser = service.saveUser(user);
		return ResponseEntity.of(Optional.of(createdUser));
		
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<UserDetails> getUserById(@PathVariable("id") Long id){
		UserDetails user = service.getUserDetailsById(id);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(user));
	}
	
	@DeleteMapping("user/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
		service.deleteUserById(id);
		return ResponseEntity.ok("User Deleted with Id: " + id);
	}
	
	@PutMapping(path="user/{id}", produces= {"application/xml"}, consumes = {"application/xml"})
	public ResponseEntity<UserDetails> updateUser(@PathVariable Long id, @RequestBody UserDetails userDetails)
			throws Exception {

		UserDetails user = service.getUserDetailsById(id);
	
		user.setId(userDetails.getId());
		user.setAge(userDetails.getAge());
		user.setName(userDetails.getName());
		user.setOccupation(userDetails.getOccupation());
		user.setPhoneNumber(userDetails.getPhoneNumber());
		user.setLocation(userDetails.getLocation());

		final UserDetails updatedUser = service.updateUserDetailsById(user, id);
		return ResponseEntity.ok(updatedUser);

	}

}
