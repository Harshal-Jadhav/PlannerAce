package com.plannerAce.Payload.Response;

import lombok.Data;

@Data
public class CandidateResponse {

	private Long id;
	private String name;
	private String email;
	private String phoneNumber;
	private String resumeDriveLink;
}
