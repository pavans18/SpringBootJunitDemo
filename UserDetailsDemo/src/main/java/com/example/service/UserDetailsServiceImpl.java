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
		if (allUsers.isEmpty()) {
			throw new ResourceNotFoundException("There are no users present");
		}
		return allUsers;
	}

	@Override
	public UserDetails saveUser(UserDetails userDetails) {

		return userDetailsRepository.save(userDetails);
	}

	@Override
	public UserDetails deleteUserById(Long id) throws ResourceNotFoundException {
		UserDetails userDetails = userDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UserDetails with Id: " + id + "Not present"));
		userDetailsRepository.deleteById(id);
		return userDetails;
	}

	@Override
	public UserDetails updateUserDetailsById(UserDetails userDetails) throws Exception {

		UserDetails user = userDetailsRepository.findById(userDetails.getId()).orElseThrow(
				() -> new ResourceNotFoundException("User with Id: " + userDetails.getId() + "Not present to update"));

		

		user.setId(userDetails.getId());
		user.setAge(userDetails.getAge());
		user.setName(userDetails.getName());
		user.setOccupation(userDetails.getOccupation());
		user.setPhoneNumber(userDetails.getPhoneNumber());
		user.setLocation(userDetails.getLocation());

		return userDetailsRepository.save(user);
	}

	@Override
	public UserDetails getUserDetailsById(Long id) {

		return userDetailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with Id: " + id + "not present"));
	}

}
