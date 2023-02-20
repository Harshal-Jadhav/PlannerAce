package com.plannerAce.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Interviewer;
import com.plannerAce.Payload.Request.InterviewerRequest;
import com.plannerAce.Payload.Response.InterviewerResponse;
import com.plannerAce.Services.InterviewerService;

@RestController("/interviewer")
public class InterviewerController {

	private InterviewerService interviewerService;

	public InterviewerController(InterviewerService interviewerService) {
		this.interviewerService = interviewerService;
	}

	@PostMapping("/add")
	public ResponseEntity<InterviewerResponse> registerInterviewer(@RequestBody InterviewerRequest interviewerRequest) {
		ModelMapper mapper = new ModelMapper();
		Interviewer interviewer = mapper.map(interviewerRequest, Interviewer.class);
		Interviewer newInterviewer = interviewerService.saveInterviewer(interviewer);
		InterviewerResponse response = mapper.map(newInterviewer, InterviewerResponse.class);
		return new ResponseEntity<InterviewerResponse>(response, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<InterviewerResponse> getInterviewerById(@PathVariable("id") Long interviewerId)
			throws InterviewerNotFoundException {
		Interviewer interviewer = interviewerService.getInterviewerById(interviewerId);
		ModelMapper mapper = new ModelMapper();
		InterviewerResponse response = mapper.map(interviewer, InterviewerResponse.class);
		return new ResponseEntity<InterviewerResponse>(response, HttpStatus.OK);

	}
}
