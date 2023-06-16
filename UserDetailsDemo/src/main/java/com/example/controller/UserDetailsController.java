package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.example.request.GetUserRequest;
import com.example.request.UserCreateRequest;
import com.example.request.UserDeleteRequest;
import com.example.request.UserUpdateRequest;
import com.example.response.GetUserResponse;
import com.example.response.UserCreateResponse;
import com.example.response.UserDeleteResponse;
import com.example.response.UserUpdateResponse;
import com.example.service.UserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserDetailsController {
	
	@Autowired
	private UserDetailsService service;
	
	
	@GetMapping(path="/getAllUsers", produces= {"application/xml"})
	public ResponseEntity<List<UserDetails>> getAllUsers(){
		List<UserDetails> users = service.getAllUsers();
		if(users.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return new ResponseEntity<List<UserDetails>>(users, HttpStatus.OK);
	}
	
	@PostMapping(path="/saveUser", produces= {"application/xml"}, consumes = {"application/xml"})
	public ResponseEntity<UserCreateResponse> createUser(@RequestBody @Valid  UserCreateRequest userRequest){
		
		UserDetails request = userRequest.getUserDetails();
		
		UserDetails saveUser = service.saveUser(request);
		
		UserCreateResponse response = new UserCreateResponse();
		
		response.setUserDetails(saveUser);
		
	 return new ResponseEntity<UserCreateResponse>(response, HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/user/detailsById", produces = { MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<GetUserResponse> getUserById(@RequestBody GetUserRequest userRequest) throws Exception{
		
		Long id = userRequest.getId();
		
		UserDetails userDetails = service.getUserDetailsById(id);
		
		GetUserResponse userResponse = new GetUserResponse();
		
		userResponse.setUserDetails(userDetails);
		
		return new ResponseEntity<GetUserResponse>(userResponse, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "users", produces = {MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_XML_VALUE} )
	public ResponseEntity<UserDeleteResponse> deleteUserById(@RequestBody UserDeleteRequest userDeleteRequest) throws ResourceNotFoundException{
	
		Long id = userDeleteRequest.getId();
		
		UserDetails deleteUserById = service.deleteUserById(id);
		
		UserDeleteResponse userResponse = new UserDeleteResponse();
		
		userResponse.setUserDetails(deleteUserById);
		
		return new ResponseEntity<UserDeleteResponse>(userResponse, HttpStatus.OK);
		
		
	}
	
	@PutMapping(path="users", produces= {"application/xml"}, consumes = {"application/xml"})
	public ResponseEntity<UserUpdateResponse> updateUser(@Valid @RequestBody  UserUpdateRequest updateRequest)
			throws Exception {
		
		UserDetails user = updateRequest.getUserDetails();
		
		UserDetails updatedUser = service.updateUserDetailsById(user);
		
		UserUpdateResponse updateResponse = new UserUpdateResponse();
		
		updateResponse.setUserDetails(updatedUser);
		
		return new ResponseEntity<UserUpdateResponse>(updateResponse, HttpStatus.OK);
		
	}

}
