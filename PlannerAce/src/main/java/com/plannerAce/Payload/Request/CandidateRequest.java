package com.plannerAce.Payload.Request;

import lombok.Data;

@Data
public class CandidateRequest {

	private String name;
	private String email;
	private String password;
	private String phoneNumber;
	private String resumeDriveLink;
}
