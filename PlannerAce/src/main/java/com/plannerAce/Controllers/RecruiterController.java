package com.plannerAce.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Models.Candidate;
import com.plannerAce.Models.Feedback;
import com.plannerAce.Services.CandidateService;
import com.plannerAce.Services.FeedbackService;
import com.plannerAce.Services.InterviewService;
import com.plannerAce.Services.InterviewerService;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {

	private InterviewerService interviewerService;
	private InterviewService interviewService;
	private FeedbackService feedbackService;
	private CandidateService candidateService;

	public RecruiterController(InterviewerService interviewerService, InterviewService interviewService,
			FeedbackService feedbackService, CandidateService candidateService) {
		this.interviewerService = interviewerService;
		this.interviewService = interviewService;
		this.feedbackService = feedbackService;
		this.candidateService = candidateService;
	}

	@GetMapping("/candidates")
	public ResponseEntity<List<Candidate>> getAllCandidates() {
		List<Candidate> candidates = candidateService.getAllCandidates();
		return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
	}

	@DeleteMapping("/candidate/{candidateId}")
	public ResponseEntity<String> deleteCandidate(@PathVariable Long candididateId) throws CandidateNotFoundException {
		candidateService.deleteCandidate(candididateId);
		return new ResponseEntity<String>("Candidate Deleted", HttpStatus.OK);
	}

	@GetMapping("/feedback/{interviewId}")
	public ResponseEntity<Feedback> getFeedbackByInterviewId(@PathVariable Long interviewId)
			throws InterviewNotFoundException {
		Feedback feedback = feedbackService.getFeedbackByInterviewId(interviewId);
		return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
	}

	@GetMapping("/feedback/candidate/{candidateId}")
	public ResponseEntity<List<Feedback>> getFeedbackByCandidate(@PathVariable Long candidateId)
			throws CandidateNotFoundException {
		List<Feedback> feedbacks = feedbackService.getFeedbackByCandidateId(candidateId);
		return new ResponseEntity<List<Feedback>>(feedbacks, HttpStatus.OK);
	}
}
