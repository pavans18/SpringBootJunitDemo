package com.example.entity;

import java.time.LocalDateTime;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Error {
	
	private String errorCode;
	
	private List<String> errorDescription;
	
	private LocalDateTime timeStamp;

}
