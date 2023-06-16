package com.example;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.entity.Location;
import com.example.entity.UserDetails;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserDetailsRepository;
import com.example.request.GetUserRequest;
import com.example.request.UserCreateRequest;
import com.example.request.UserDeleteRequest;
import com.example.service.UserDetailsService;
import com.example.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


@WebMvcTest
public class UserDetailsRepoTest {

	
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserDetailsRepository detailsRepository;

	@MockBean
	private UserDetailsService detailsService;

	@InjectMocks
	private UserDetailsServiceImpl detailsServiceImpl;

	private ObjectMapper objectMapper = new XmlMapper();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testDeleteUserByIdRepoTest() throws Exception {

		UserDeleteRequest deleteRequest = UserDeleteRequest.builder().id(2L).build();

		// Long id = 15L;
		String requestURI = "/api/users";

		UserDetails userDetails1 = new UserDetails();
		userDetails1.setId(2L);
		userDetails1.setName("Vinod");
		userDetails1.setPhoneNumber("7821251214");
		userDetails1.setOccupation("Engineer");
		userDetails1.setAge(25);

		Location location1 = new Location();
		location1.setId((long) 9);
		location1.setCity("New York");
		location1.setState("NY");
		location1.setCountry("USA");
		userDetails1.setLocation(location1);

		String requestBody = objectMapper.writeValueAsString(userDetails1);

		when(detailsService.deleteUserById(deleteRequest.getId())).thenReturn(userDetails1);

		mockMvc.perform(delete(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
				.content(requestBody)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testAddUserRepo() {

		UserDetails user = UserDetails.builder().id(1L).name("pavan").phoneNumber("46589778").occupation("tester")
				.age(23).location(Location.builder().id(1L).city("HYD").state("Telangana").country("india").build())
				.build();

		when(detailsRepository.save(user)).thenReturn(user);

		UserDetails result = detailsServiceImpl.saveUser(user);

		Assertions.assertEquals(1L, result.getId());
		Assertions.assertEquals("pavan", result.getName());

		verify(detailsRepository, times(1)).save(user);
	}

	@Test
	public void testAddUSerHasFieldsErrorRepo() {

		UserDetails user = UserDetails.builder().id(1L).name("").phoneNumber("98761234").occupation("").age(23)
				.location(Location.builder().id(1L).city("HYD").state("Telangana").country("india").build()).build();

		when(detailsRepository.save(user)).thenThrow(RuntimeException.class);

		Assertions.assertThrows(RuntimeException.class, () -> detailsServiceImpl.saveUser(user));

		verify(detailsRepository, times(1)).save(user);
	}

	@Test
	public void testGetUserByIdExistsRepo() throws ResourceNotFoundException {

		UserDetails user = UserDetails.builder().id(1L).name("pavan").phoneNumber("9876123451").occupation("tester")
				.age(23).location(Location.builder().id(1L).city("HYD").state("Telangana").country("india").build())
				.build();

		when(detailsRepository.findById(user.getId())).thenReturn(Optional.of(user));

		UserDetails result = detailsServiceImpl.getUserDetailsById(user.getId());

		Assertions.assertEquals("pavan", result.getName());

		verify(detailsRepository, times(1)).findById(1L);
	}

	@Test
	public void testGetUserByIdNotFoundRepo() {

		when(detailsRepository.findById(1L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> detailsServiceImpl.getUserDetailsById(1L));

		verify(detailsRepository, times(1)).findById(1L);
	}

	@Test
	public void testDeleteUserByIdExistsRepo() throws ResourceNotFoundException {

		UserDetails user = UserDetails.builder().id(1L).name("pavan").phoneNumber("9876123451").occupation("tester")
				.age(23).location(Location.builder().id(1L).city("HYD").state("Telangana").country("india").build())
				.build();

		when(detailsRepository.findById(user.getId())).thenReturn(Optional.of(user));

		UserDetails result = detailsServiceImpl.deleteUserById(user.getId());

		Assertions.assertEquals(1, result.getId());
		Assertions.assertEquals("pavan", result.getName());

		verify(detailsRepository, times(1)).findById(1L);
		verify(detailsRepository, times(1)).deleteById(1L);

	}

	@Test
	public void testDeleteUserByIdNotFoundRepo() {

		when(detailsRepository.findById(1L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> detailsServiceImpl.deleteUserById(1L));

		verify(detailsRepository, times(1)).findById(1L);
		verify(detailsRepository, times(0)).deleteById(1L);
	}

	@Test
	public void testUpdateUserByIdExistsRepo() throws Exception {

		UserDetails currentUser = UserDetails.builder().id(1L).name("jay").phoneNumber("9876123451")
				.occupation("tester").age(23)
				.location(Location.builder().id(1L).city("HYD").state("Telangana").country("india").build()).build();

		UserDetails updatedUser = UserDetails.builder().id(1L).name("Dev").phoneNumber("1234567899")
				.occupation("developer").age(32)
				.location(Location.builder().id(1L).city("Chennai").state("TamilNadu").country("india").build())
				.build();

		when(detailsRepository.findById(currentUser.getId())).thenReturn(Optional.of(currentUser));

		when(detailsRepository.save(currentUser)).thenReturn(updatedUser);

		UserDetails result = detailsServiceImpl.updateUserDetailsById(updatedUser);

		Assertions.assertEquals("Dev", result.getName());
		Assertions.assertEquals("Chennai", result.getLocation().getCity());

		verify(detailsRepository, times(1)).findById(currentUser.getId());
		verify(detailsRepository, times(1)).save(currentUser);
	}

	@Test
	public void testUpdateUserByIdNotFoundRepo() {

		UserDetails updatedUser = UserDetails.builder().id(1L).name("charan").phoneNumber("1234567899").occupation("developer").age(32)
				.location(Location.builder().id(1L).city("Chennai").state("TamilNadu").country("india").build())
				.build();

		when(detailsRepository.findById(updatedUser.getId())).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> detailsServiceImpl.updateUserDetailsById(updatedUser));

		verify(detailsRepository, times(1)).findById(updatedUser.getId());
		verify(detailsRepository, times(0)).save(any(UserDetails.class));
	}
	
	 	@Test
	    public void testGetAllUsersRepo() {

	        List<UserDetails> users = new ArrayList<>();
	       
	        users.add(UserDetails.builder().id(1L).name("prem").phoneNumber("17974897").occupation("Architect").age(32)
	                .location(Location.builder().id(1L).city("Bangalore").state("Karnataka").country("India").build())
	                .build());
	        
	        
	        users.add(UserDetails.builder().id(1L).name("Alok").phoneNumber("4687878658").occupation("tester").age(23)
	                .location(Location.builder().id(1L).city("HYD").state("Telangana").country("India").build())
	                .build());

	        when(detailsRepository.findAll()).thenReturn(users);

	        List<UserDetails> result = detailsServiceImpl.getAllUsers();

	        Assertions.assertEquals(2, result.size());
	        Assertions.assertEquals("prem", result.get(0).getName());
	        Assertions.assertEquals("Alok", result.get(1).getName());

	        verify(detailsRepository, times(1)).findAll();
	    }
	 	
		
	
}
