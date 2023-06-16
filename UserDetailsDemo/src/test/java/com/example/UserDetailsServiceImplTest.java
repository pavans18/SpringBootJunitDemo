package com.example;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@WebMvcTest
public class UserDetailsServiceImplTest {

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
	public void testAllUsersFound() throws Exception {

		String requestURI = "/api/getAllUsers";

		UserDetails userDetails1 = new UserDetails();
		userDetails1.setId(1L);
		userDetails1.setName("Rohan");
		userDetails1.setPhoneNumber("121485275");
		userDetails1.setOccupation("Engineer");
		userDetails1.setAge(25);

		Location location1 = new Location();
		location1.setId((long) 9);
		location1.setCity("New York");
		location1.setState("NY");
		location1.setCountry("USA");
		userDetails1.setLocation(location1);

		UserDetails userDetails2 = new UserDetails();
		userDetails2.setId(2L);
		userDetails2.setName("Tom");
		userDetails2.setPhoneNumber("78451346546");
		userDetails2.setOccupation("Architect");
		userDetails2.setAge(25);

		Location location2 = new Location();
		location2.setId((long) 10);
		location2.setCity("Pune");
		location2.setState("Maharashtra");
		location2.setCountry("India");
		userDetails2.setLocation(location2);

		List<UserDetails> userList = new ArrayList<>();
		userList.add(userDetails1);
		userList.add(userDetails2);

		Mockito.when(detailsService.getAllUsers()).thenReturn(userList);

		mockMvc.perform(get(requestURI)).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void testAllUsersNotFound() throws Exception {

		String requestURI = "/api/getAllUsers";

		// Mockito.when(detailsService.getAllUsers()).thenReturn(new ArrayList<>());
		Mockito.when(detailsRepository.findAll()).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(requestURI)).andExpect(status().isNotFound()).andDo(print());

	}

	@Test
	public void testAddNewUserDetails() throws Exception {

		String requestURI = "/api/saveUser";

		UserDetails userDetails1 = new UserDetails();
		userDetails1.setId(1L);
		userDetails1.setName("Prince");
		userDetails1.setPhoneNumber("7841028214");
		userDetails1.setOccupation("Engineer");
		userDetails1.setAge(25);

		Location location1 = new Location();
		location1.setId((long) 9);
		location1.setCity("New York");
		location1.setState("NY");
		location1.setCountry("USA");
		userDetails1.setLocation(location1);

		Mockito.when(detailsRepository.save(userDetails1)).thenReturn(userDetails1);

		String requestBody = objectMapper.writeValueAsString(userDetails1);

		mockMvc.perform(post(requestURI).contentType("application/xml").content(requestBody))

				.andExpect(status().isCreated()).andDo(print());

	}

	@Test
	public void testDeleteUserById() throws Exception {

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

		Mockito.when(detailsService.deleteUserById(deleteRequest.getId())).thenReturn(userDetails1);

		mockMvc.perform(delete(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
				.content(requestBody)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testDeleteUserNotFound() throws Exception {
		Long id = 55L;
		String requestURI = "/api/user/" + id;

		Mockito.doThrow(ResourceNotFoundException.class).when(detailsService).deleteUserById(id);

		mockMvc.perform(delete(requestURI)).andExpect(status().isNotFound()).andDo(print());

	}

	@Test
	public void testGetUserByNotFound() throws Exception {

		String requestURI = "/api/user/detailsById";

		UserDetails userDetails = new UserDetails();

		userDetails.setId(null);
		userDetails.setName("");
		userDetails.setAge(0);
		userDetails.setOccupation("");
		userDetails.setPhoneNumber("");
		userDetails.setLocation(null);

		String requestBody = objectMapper.writeValueAsString(userDetails);

		Mockito.when(detailsService.getUserDetailsById(userDetails.getId())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
				.content(requestBody)).andExpect(status().isNotFound()).andDo(print());

	}

	@Test
	public void testAddNewUserOk() throws Exception {

		String requestURI = "/api/saveUser";

		UserDetails userDetails = new UserDetails();
		userDetails.setId(2L);
		userDetails.setName("Vinod");
		userDetails.setPhoneNumber("7821251214");
		userDetails.setOccupation("Engineer");
		userDetails.setAge(25);

		Location location = new Location();
		location.setId((long) 9);
		location.setCity("New York");
		location.setState("NY");
		location.setCountry("USA");
		userDetails.setLocation(location);

		String requestBody = objectMapper.writeValueAsString(userDetails);

		Mockito.when(detailsService.saveUser(userDetails)).thenReturn(userDetails);

		mockMvc.perform(post(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
				.content(requestBody)).andExpect(status().isCreated()).andDo(print());

	}

	@Test
	public void testAddUserHasBadRequest() throws Exception {

		String requestURI = "/api/saveUser";

		UserCreateRequest request = UserCreateRequest.builder()
				.userDetails(
						UserDetails.builder().id(null).name("").phoneNumber("").occupation("").location(null).build())
				.build();

		String requestBody = objectMapper.writeValueAsString(request);

		mockMvc.perform(post(requestURI).contentType(MediaType.APPLICATION_XML).content(requestBody))
				.andExpect(status().isBadRequest()).andDo(print());

	}

	@Test
	public void saveNewUser() throws Exception {

		String requestURI = "/api/saveUser";

		UserDetails userDetails = new UserDetails();
		userDetails.setId(2L);
		userDetails.setName("Vinod");
		userDetails.setPhoneNumber("7821251214");
		userDetails.setOccupation("Engineer");
		userDetails.setAge(25);

		Location location = new Location();
		location.setId((long) 9);
		location.setCity("New York");
		location.setState("NY");
		location.setCountry("USA");
		userDetails.setLocation(location);

		String requestBody = objectMapper.writeValueAsString(userDetails);

		Mockito.when(detailsRepository.save(userDetails)).thenReturn(userDetails);

		mockMvc.perform(post(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
				.content(requestBody)).andExpect(status().isCreated()).andDo(print());

	}

	
	
}
