package com.plannerAce.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Feedback;
import com.plannerAce.Models.Interview;
import com.plannerAce.Models.Interviewer;
import com.plannerAce.Payload.Request.FeedbackRequest;
import com.plannerAce.Payload.Request.InterviewerRequest;
import com.plannerAce.Payload.Response.InterviewerResponse;
import com.plannerAce.Services.FeedbackService;
import com.plannerAce.Services.InterviewService;
import com.plannerAce.Services.InterviewerService;

@RestController("/interviewer")
public class InterviewerController {

	private InterviewerService interviewerService;
	private FeedbackService feedbackService;
	private InterviewService interviewService;

	public InterviewerController(InterviewerService interviewerService, FeedbackService feedbackService,
			InterviewService interviewService) {
		this.interviewerService = interviewerService;
		this.feedbackService = feedbackService;
		this.interviewService = interviewService;
	}

	@PostMapping("/add_interviewer")
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

	@PutMapping("/{interviewerId}/update")
	public ResponseEntity<Interviewer> updateInterviewer(@PathVariable Long interviewerId,
			@RequestBody Interviewer interviewer) throws InterviewerNotFoundException {
		Interviewer updatedInterviewer = interviewerService.updateInterviewer(interviewerId, interviewer);
		return new ResponseEntity<Interviewer>(updatedInterviewer, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{interviewerId}")
	public ResponseEntity<String> deleteInterviewer(@PathVariable Long interviewerId)
			throws InterviewerNotFoundException {
		interviewerService.deleteInterviewer(interviewerId);
		String message = "Interviewer Deleted sucessfully";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@PostMapping("/{interviewerId}/feedback/{interviewId}")
	public ResponseEntity<Feedback> addFeedback(@PathVariable Long interviewerId, @PathVariable Long interviewId,
			@RequestBody FeedbackRequest feedbackRequest)
					throws InterviewNotFoundException, InterviewerNotFoundException, CandidateNotFoundException {
		Feedback feedback = feedbackService.addFeedback(interviewId, interviewerId, feedbackRequest);
		return new ResponseEntity<Feedback>(feedback, HttpStatus.CREATED);
	}

	@GetMapping("/interviews/{interviewId}")
	// TODO add a checking for the interview is of the interviewer requesting.
	public ResponseEntity<Interview> getInterviewById(@PathVariable Long interviewId)
			throws InterviewNotFoundException {
		Interview interview = interviewService.getInterviewById(interviewId);
		return new ResponseEntity<Interview>(interview, HttpStatus.OK);
	}
}
