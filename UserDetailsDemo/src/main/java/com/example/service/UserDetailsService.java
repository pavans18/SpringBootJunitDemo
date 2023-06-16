package com.example.service;

import java.util.List;

import com.example.entity.UserDetails;

public interface UserDetailsService {
	
	public UserDetails getUserDetailsById(Long id);
	
	public List<UserDetails> getAllUsers();
	
	public UserDetails saveUser(UserDetails userDetails);
	
	public UserDetails deleteUserById(Long id);
	
	public UserDetails updateUserDetailsById(UserDetails userDetails) throws Exception;
	
	

}
