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
@Builder
@NoArgsConstructor
@JacksonXmlRootElement(localName = "update-user-request")
public class UserUpdateRequest {
	
	@Valid
	private UserDetails userDetails; 

}
