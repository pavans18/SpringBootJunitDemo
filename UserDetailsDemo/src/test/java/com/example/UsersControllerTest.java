package com.example;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer.AmbiguousBindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;


import com.example.controller.UserDetailsController;
import com.example.entity.Location;
import com.example.entity.UserDetails;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserDetailsRepository;
import com.example.request.GetUserRequest;
import com.example.request.UserCreateRequest;
import com.example.request.UserDeleteRequest;
import com.example.request.UserUpdateRequest;
import com.example.service.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@WebMvcTest
public class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new XmlMapper();

	@Mock
	private UserDetailsRepository userDetailsRepository;

	@MockBean
	private UserDetailsService detailsService;

	@InjectMocks
	private UserDetailsController userDetailsController;

	

	@Test
	public void testDeleteUserNotFound() throws Exception {
		Long id = 55L;
		String requestURI = "/api/users";

		Mockito.doThrow(ResourceNotFoundException.class).when(detailsService).deleteUserById(id);

		mockMvc.perform(delete(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)).andExpect(status().isBadRequest()).andDo(print());

	}

	@Test
	public void testAllUsers() throws Exception {

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

		mockMvc.perform(get(requestURI)).andExpect(status().isOk())
				.andDo(print());


	}

	@Test
	public void testAllUsersNotFound() throws Exception {

		String requestURI = "/api/getAllUsers";

		//Mockito.when(detailsService.getAllUsers()).thenReturn(new ArrayList<>());
		Mockito.when(detailsService.getAllUsers()).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(requestURI)).andExpect(status().isNotFound()).andDo(print());

	}

	@Test
	public void testGetUserById() throws Exception {

		Long id = 1L;
		String requestURI = "/api/user/detailsById";

		UserDetails userDetails = new UserDetails();
		userDetails.setId(id);
		userDetails.setName("Rohan");
		userDetails.setPhoneNumber("121485275");
		userDetails.setOccupation("Engineer");
		userDetails.setAge(25);

		Location location = new Location();
		location.setId((long) 9);
		location.setCity("New York");
		location.setState("NY");
		location.setCountry("USA");
		userDetails.setLocation(location);

		String requestBody = objectMapper.writeValueAsString(userDetails);

		Mockito.when(detailsService.getUserDetailsById(id)).thenReturn(userDetails);

		mockMvc.perform(get(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).content(requestBody)).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void testGetUserByIdNotFound() throws Exception {
		Long id = 50L;
		String requestURI = "/api/user/detailsById";

		Mockito.when(detailsService.getUserDetailsById(id)).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(requestURI)).andExpect(status().isBadRequest()).andDo(print());
	}
	


	@Test
	public void testAddNewUser() throws Exception {

		String requestURI = "/api/saveUser";

		UserDetails userDetails1 = new UserDetails();
		userDetails1.setId(1L);
		userDetails1.setName("Prashanth");
		userDetails1.setPhoneNumber("78111546");
		userDetails1.setOccupation("Engineer");
		userDetails1.setAge(25);

		Location location1 = new Location();
		location1.setId((long) 9);
		location1.setCity("New York");
		location1.setState("NY");
		location1.setCountry("USA");
		userDetails1.setLocation(location1);

		Mockito.when(detailsService.saveUser(userDetails1)).thenReturn(userDetails1);

		String requestBody = objectMapper.writeValueAsString(userDetails1);

		mockMvc.perform(post(requestURI).contentType("application/xml").content(requestBody))

				.andExpect(status().isCreated()).andDo(print());

	}
	
//	@Test
//	public void testAddUserHasError() throws Exception {
//
//		String requestURI = "/api/saveUser";
//
//		
//		UserDetails userDetails1 = new UserDetails();
//		userDetails1.setId(null);
//		userDetails1.setName("");
//		userDetails1.setPhoneNumber("");
//		userDetails1.setOccupation("");
//		userDetails1.setAge(0);
//
//		Location location1 = new Location();
//		location1.setId((long) 9);
//		location1.setCity(" ");
//		location1.setState("");
//		location1.setCountry("USA");
//		userDetails1.setLocation(location1);
//
//		when(detailsService.saveUser(userDetails1)).thenThrow(ResourceNotFoundException.class);
//
//		String requestBody = objectMapper.writeValueAsString(userDetails1);
//
//		mockMvc.perform(post(requestURI).contentType(MediaType.APPLICATION_XML).content(requestBody))
//
//				.andExpect(status().isBadRequest()).andDo(print());
//
//	}
	
	

	@Test
	public void testAddUserNotFound() throws Exception {

		String requestURI = "/api/saveUser";

		UserCreateRequest createRequest = new UserCreateRequest();
		createRequest.setUserDetails(null);

		String requestBody = objectMapper.writeValueAsString(createRequest);

		mockMvc.perform(post(requestURI).contentType("application/xml").content(requestBody))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void testUpdateUserById() throws Exception {

		Long id = 10L;

		String requestURI = "/api/users";

		UserDetails userDetails1 = new UserDetails();
		userDetails1.setId(id);
		userDetails1.setName("Jerrys");
		userDetails1.setPhoneNumber("1545312313564");
		userDetails1.setOccupation("Architect");
		userDetails1.setAge(25);

		Location location1 = new Location();
		location1.setId((long) 9);
		location1.setCity("New York");
		location1.setState("NY");
		location1.setCountry("USA");
		userDetails1.setLocation(location1);

		Mockito.when(detailsService.updateUserDetailsById(userDetails1)).thenReturn(userDetails1);

		String requestBody = objectMapper.writeValueAsString(userDetails1);

		mockMvc.perform(put(requestURI).contentType("application/xml").content(requestBody)).andExpect(status().isOk())
				.andDo(print());

	}
	
	@Test
	public void testUpdateUser() throws Exception{
		
		String requestURI = "/api/users";
		
		UserUpdateRequest updateRequest = UserUpdateRequest.builder().userDetails(UserDetails.builder().id(1L).name("kiran").phoneNumber("2688748453").occupation("Engineer")
																				.age(25).location(Location.builder().id(1L).city("Bangalore").state("Karnataka").country("India").build()).build()).build();
	
		String requestBody = objectMapper.writeValueAsString(updateRequest);
		
		when(detailsService.updateUserDetailsById(updateRequest.getUserDetails())).thenReturn(updateRequest.getUserDetails());
		
		mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_XML).content(requestBody))
		.andExpect(status().isOk()).andDo(print());	
	
	}
	
	
	@Test
	public void testDeleteUserRequest() throws Exception{
		
		UserDeleteRequest deleteRequest = UserDeleteRequest.builder().id(2L).build();
		
		String requestURI = "/api/users";
		
		UserDetails userDetails1 = new UserDetails();
		userDetails1.setId(2L);
		userDetails1.setName("ram");
		userDetails1.setPhoneNumber("7814152218558");
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
	public void testUpdateUserRequestNotFound() throws Exception {

		String requestURI = "/api/users";
		
		UserUpdateRequest updateRequest = UserUpdateRequest.builder().userDetails(UserDetails.builder().id(1L).name("ram").phoneNumber("45454564648").occupation("Automation").age(29)
									.location(Location.builder().id(1L).city("chennai").state("tamilnadu").country("india").build()).build()).build();


		

		String requestBody = objectMapper.writeValueAsString(updateRequest);

		when(detailsService.updateUserDetailsById(updateRequest.getUserDetails())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_XML).content(requestBody))
				.andExpect(status().isNotFound()).andDo(print());

	}
	
	@Test
	public void testUpdateUserRequestBadRequest() throws Exception {

		String requestURI = "/api/users";
		
		UserUpdateRequest updateRequest = UserUpdateRequest.builder().userDetails(UserDetails.builder().id(1L).name("").phoneNumber("").occupation("AWS ENGINEER").age(29)
				.location(Location.builder().id(1L).city("").state("tamilnadu").country("india").build()).build()).build();

		String requestBody = objectMapper.writeValueAsString(updateRequest);

		when(detailsService.updateUserDetailsById(updateRequest.getUserDetails())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_XML).content(requestBody))
				.andExpect(status().isBadRequest()).andDo(print());

	}
	
	
	@Test
	public void testAddNewUserDetailsRequest() throws Exception {

		String requestURI = "/api/saveUser";
		
		UserCreateRequest createRequest = UserCreateRequest.builder().userDetails(UserDetails.builder().id(1L).name("pavan").phoneNumber("9876123451").occupation("Engineer").age(23)
				.location(Location.builder().id(1L).city("HYD").state("Telangana").country("india").build())
				.build()).build();


		when(detailsService.saveUser(createRequest.getUserDetails())).thenReturn(createRequest.getUserDetails());

		String requestBody = objectMapper.writeValueAsString(createRequest);

		mockMvc.perform(post(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
				.content(requestBody)).andExpect(status().isCreated()).andDo(print());

	}
	
	@Test
	public void testAddUserReturn400BadRequest() throws Exception {
		
		String requestURI = "/api/saveUser";
		
		UserCreateRequest request = UserCreateRequest.builder().userDetails(UserDetails.builder().id(null).name("").phoneNumber("").occupation("").location(null).build()).build();
				
		String requestBody = objectMapper.writeValueAsString(request);

		mockMvc.perform(post(requestURI).contentType(MediaType.APPLICATION_XML).content(requestBody))
				.andExpect(status().isBadRequest()).andDo(print());

	}
	
	@Test
	public void testFindUserByIdReturn404NotFound() throws Exception {
		
		String requestURI = "/api/user/detailsById";

		GetUserRequest request = GetUserRequest.builder().id(123L).build();
				
		
		String requestBody = objectMapper.writeValueAsString(request);
		
		when(detailsService.getUserDetailsById(request.getId())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(requestURI).contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML)
				.content(requestBody)).andExpect(status().isNotFound()).andDo(print());

	}
	

}
