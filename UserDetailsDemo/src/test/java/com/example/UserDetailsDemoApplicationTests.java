package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.example.controller.UserDetailsController;
import com.example.entity.Location;
import com.example.entity.UserDetails;
import com.example.repository.UserDetailsRepository;
import com.example.service.UserDetailsService;




@SpringBootTest
class UserDetailsDemoApplicationTests {

	@Autowired
	private UserDetailsService detailsService;
	
	@Mock
	private UserDetailsRepository repository;
	
	@InjectMocks
	private UserDetailsController userDetailsController;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
//	@Test
//	public void testCreatedUser() {
//		UserDetails userDetails = new UserDetails();
//		userDetails.setName("Prince");
//		userDetails.setPhoneNumber("78216521455");
//		userDetails.setOccupation("Engineer");
//		userDetails.setAge(30);
//		
//		Location location = new Location();
//		
//		location.setCity("New York");
//		location.setState("NY");
//		location.setCountry("USA");
//		userDetails.setLocation(location);
//		
//		when(repository.save(any(UserDetails.class))).thenReturn(userDetails)
//	
//	}
	
//	@Test
//	public void deleteUserByIdTest() {
//		UserDetails userDetails = new UserDetails();
//		
//		userDetails.setId((long) 8);
//		userDetails.setName("Prince");
//		userDetails.setPhoneNumber("78216521455");
//		userDetails.setOccupation("Engineer");
//		userDetails.setAge(30);
//		
//		Location location = new Location();
//		location.setId((long) 8);
//		location.setCity("New York");
//		location.setState("NY");
//		location.setCountry("USA");
//		userDetails.setLocation(location);
//		
//	
//		detailsService.deleteUserById((long) 8);
//		verify(repository, times(1)).delete(userDetails);
//	}

//	@Test
//	public void insertUserTest() {
//		
//		//Optional<UserDetails> user = Optional.of(new UserDetails(8,"nam","123455","engineer",20,8,"nyc","ny","USA"))
//		
//		UserDetails userDetails = new UserDetails();
//		
//		//userDetails.setId((long) 8);
//		userDetails.setName("Rohan");
//		userDetails.setPhoneNumber("121485275");
//		userDetails.setOccupation("Engineer");
//		userDetails.setAge(25);
//		
//		Location location = new Location();
//		//location.setId((long) 9);
//		location.setCity("New York");
//		location.setState("NY");
//		location.setCountry("USA");
//		userDetails.setLocation(location);
//		
//		when(repository.save(userDetails)).thenReturn(userDetails);
//		assertEquals(userDetails, detailsService.saveUser(userDetails));
//	}
	
	
//	@Test
//	public void testGetUserById() {
//		
//		Long userId =  4L;
//		UserDetails userDetails = new UserDetails();
//		userDetails.setId(userId);
//		userDetails.setName("Raj");
//		
//		when(repository.findById(userId)).thenReturn(Optional.of(userDetails));
//		ResponseEntity<UserDetails> response = userDetailsController.getUserById(userId);
//		assertNotNull(response);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(userDetails, response.getBody());
//		
//		verify(repository, times(1)).findById(userId);
//	}
}
