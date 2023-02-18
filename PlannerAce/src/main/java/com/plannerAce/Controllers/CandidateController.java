package com.plannerAce.Controllers;

import java.util.List;
import java.util.stream.Collectors;

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
import com.plannerAce.Models.Candidate;
import com.plannerAce.Models.Interview;
import com.plannerAce.Services.CandidateService;
import com.plannerAce.Services.InterviewService;

@RestController("/candidates")
public class CandidateController {

	private CandidateService candidateService;
	private InterviewService interviewService;

	public CandidateController(CandidateService candidateService, InterviewService interviewService) {
		this.candidateService = candidateService;
		this.interviewService = interviewService;
	}

	@PostMapping("/")
	public ResponseEntity<Candidate> registerNewCandidate(@RequestBody Candidate candidate) {
		Candidate newCandidate = candidateService.createCandidate(candidate);
		return new ResponseEntity<Candidate>(newCandidate, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Candidate> getCandidateById(@PathVariable("id") Long candidateId)
			throws CandidateNotFoundException {
		Candidate candidate = candidateService.getCandidateById(candidateId);
		return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<Candidate> updateCandidateProfile(@PathVariable("id") Long candidateId,
			@RequestBody Candidate candidate) throws CandidateNotFoundException {
		Candidate updatedCandidate = candidateService.updateCandidate(candidateId, candidate);
		return new ResponseEntity<Candidate>(updatedCandidate, HttpStatus.OK);
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

}
