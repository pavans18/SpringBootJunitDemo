package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.UserDetails;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserDetailsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Override
	public List<UserDetails> getAllUsers() {
		List<UserDetails> allUsers = userDetailsRepository.findAll();
		if(allUsers.isEmpty()) {
			throw new ResourceNotFoundException("There are no users present");
		}
		return allUsers;
	}

	@Override
	public UserDetails saveUser(UserDetails userDetails) {
	
		return userDetailsRepository.save(userDetails);
	}

	@Override
	public void deleteUserById(Long id) {
		UserDetails userDetails = userDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UserDetails with Id: " + id + "Not present"));
		userDetailsRepository.delete(userDetails);
		
	}

	@Override
	public UserDetails updateUserDetailsById(UserDetails userDetails, Long id) throws Exception {
	
		UserDetails user = userDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with Id: " + id + "Not present to update"));
		return userDetailsRepository.save(userDetails);
	}

	@Override
	public UserDetails getUserDetailsById(Long id) {

		return userDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with Id: " + id + "not present"));
	}

	
		

	

}
