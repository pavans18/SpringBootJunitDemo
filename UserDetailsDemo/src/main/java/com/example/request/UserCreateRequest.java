package com.example.request;

import com.example.entity.UserDetails;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "create-user-request")
public class UserCreateRequest {
	
	@Valid
	private UserDetails userDetails;

	
	}
	
	


