package com.plannerAce.Payload.Request;

import lombok.Data;

@Data
public class InterviewerRequest {

	private Long id;
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
}
