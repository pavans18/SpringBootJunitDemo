package com.example.response;

import com.example.entity.UserDetails;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "get-user-detailsById-response")
public class GetUserResponse {

	private UserDetails userDetails;
}
