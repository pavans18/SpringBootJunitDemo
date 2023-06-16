package com.example.response;

import com.example.entity.UserDetails;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JacksonXmlRootElement(localName = "create-user-details-response")
public class UserCreateResponse {

	private UserDetails userDetails;
}
