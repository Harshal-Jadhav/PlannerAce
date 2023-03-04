package com.plannerAce.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plannerAce.Enums.InterviewStatus;
import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Models.Candidate;
import com.plannerAce.Models.Feedback;
import com.plannerAce.Models.Interview;
import com.plannerAce.Payload.Request.CandidateRequest;
import com.plannerAce.Payload.Response.CandidateResponse;
import com.plannerAce.Services.CandidateService;
import com.plannerAce.Services.FeedbackService;
import com.plannerAce.Services.InterviewService;

@RestController("/candidates")
public class CandidateController {

	private CandidateService candidateService;
	private InterviewService interviewService;
	private FeedbackService feedbackService;

	public CandidateController(CandidateService candidateService, InterviewService interviewService,
			FeedbackService feedbackService) {
		this.candidateService = candidateService;
		this.interviewService = interviewService;
		this.feedbackService = feedbackService;
	}

	@PostMapping("/add")
	public ResponseEntity<CandidateResponse> registerNewCandidate(@RequestBody CandidateRequest candidateRequest) {
		ModelMapper mapper = new ModelMapper();
		Candidate candidate = mapper.map(candidateRequest, Candidate.class);
		Candidate newCandidate = candidateService.createCandidate(candidate);
		CandidateResponse candidateResponse = mapper.map(newCandidate, CandidateResponse.class);
		return new ResponseEntity<CandidateResponse>(candidateResponse, HttpStatus.CREATED);
	}

	@GetMapping("/{candidate_id}")
	public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable("candidate_id") Long candidateId)
			throws CandidateNotFoundException {
		Candidate candidate = candidateService.getCandidateById(candidateId);
		ModelMapper mapper = new ModelMapper();
		CandidateResponse candidateResponse = mapper.map(candidate, CandidateResponse.class);
		return new ResponseEntity<CandidateResponse>(candidateResponse, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<CandidateResponse> updateCandidateProfile(@PathVariable("id") Long candidateId,
			@RequestBody Candidate candidate) throws CandidateNotFoundException {
		Candidate updatedCandidate = candidateService.updateCandidate(candidateId, candidate);
		ModelMapper mapper = new ModelMapper();
		CandidateResponse candidateResponse = mapper.map(updatedCandidate, CandidateResponse.class);
		return new ResponseEntity<CandidateResponse>(candidateResponse, HttpStatus.OK);
	}

	@GetMapping("/{id}/interviews/scheduled")
	public ResponseEntity<List<Interview>> getScheduledInterviews(@PathVariable("id") Long candidateId) {
		List<Interview> scheduledInterviews = interviewService.getInterviewsByCandidateId(candidateId).stream()
				.filter(i -> i.getStatus().equals(InterviewStatus.SCHEDULED)).collect(Collectors.toList());

		return new ResponseEntity<List<Interview>>(scheduledInterviews, HttpStatus.OK);
	}

	@GetMapping("/{id}/interviews/completed")
	public ResponseEntity<List<Interview>> getCompletedInterviews(@PathVariable("id") Long candidateId) {
		List<Interview> completedInterviews = interviewService.getInterviewsByCandidateId(candidateId).stream()
				.filter(i -> i.getStatus().equals(InterviewStatus.COMPLETED)).collect(Collectors.toList());

		return new ResponseEntity<List<Interview>>(completedInterviews, HttpStatus.OK);
	}

	@GetMapping("/{id}/interviews/cancelled")
	public ResponseEntity<List<Interview>> getCancelledInterviews(@PathVariable("id") Long candidateId) {
		List<Interview> cancelledInterviews = interviewService.getInterviewsByCandidateId(candidateId).stream()
				.filter(i -> i.getStatus().equals(InterviewStatus.CANCELLED)).collect(Collectors.toList());

		return new ResponseEntity<List<Interview>>(cancelledInterviews, HttpStatus.OK);
	}

	@GetMapping("/{id}/interviews/all")
	public ResponseEntity<List<Interview>> getAllInterviews(@PathVariable("id") Long candidateId) {
		List<Interview> allInterviews = interviewService.getInterviewsByCandidateId(candidateId);

		return new ResponseEntity<List<Interview>>(allInterviews, HttpStatus.OK);
	}


	@GetMapping("/interviews/{interviewId}")
	// TODO add a checking for the interview is of the cadidate requesting.
	public ResponseEntity<Interview> getInterviewById(@PathVariable Long interviewId)
			throws InterviewNotFoundException {
		Interview interview = interviewService.getInterviewById(interviewId);
		return new ResponseEntity<Interview>(interview, HttpStatus.OK);
	}

	@GetMapping("/interview/feedback/{interviewerId}")
	public ResponseEntity<Feedback> getFeedbackByInterviewId(@PathVariable Long interviewerId)
			throws InterviewNotFoundException {
		Feedback feedback = feedbackService.getFeedbackByInterviewId(interviewerId);
		return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
	}
}
