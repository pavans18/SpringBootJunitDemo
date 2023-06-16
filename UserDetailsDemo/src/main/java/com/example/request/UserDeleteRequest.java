package com.example.request;

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
@JacksonXmlRootElement(localName = "delete-user-request")
public class UserDeleteRequest {
	
	@Valid
	private Long id;

	

}
