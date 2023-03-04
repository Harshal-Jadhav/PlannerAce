package com.plannerAce.Payload.Request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewInterviewRequest {

	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String location;
	private String comments;
	private Long candidiateId;
	private Long interviewerId;
}
