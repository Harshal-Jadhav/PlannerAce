package com.plannerAce.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewScheduleConflictException;
import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Candidate;
import com.plannerAce.Models.Feedback;
import com.plannerAce.Models.Interview;
import com.plannerAce.Payload.Request.NewInterviewRequest;
import com.plannerAce.Services.CandidateService;
import com.plannerAce.Services.FeedbackService;
import com.plannerAce.Services.InterviewService;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {

	private InterviewService interviewService;
	private FeedbackService feedbackService;
	private CandidateService candidateService;

	public RecruiterController(InterviewService interviewService,
			FeedbackService feedbackService, CandidateService candidateService) {
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
	public ResponseEntity<Feedback> getFeedbackOfInterview(@PathVariable Long interviewId)
			throws InterviewNotFoundException {
		Feedback feedback = feedbackService.getFeedbackByInterviewId(interviewId);
		return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
	}

	@GetMapping("/feedback/candidate/{candidateId}")
	public ResponseEntity<List<Feedback>> getFeedbacksOfCandidate(@PathVariable Long candidateId)
			throws CandidateNotFoundException {
		List<Feedback> feedbacks = feedbackService.getFeedbackByCandidateId(candidateId);
		return new ResponseEntity<List<Feedback>>(feedbacks, HttpStatus.OK);
	}

	@GetMapping("/interviews/{interviewStatus}")
	public ResponseEntity<List<Interview>> getAllInterviews(@PathVariable String interviewStatus) {

		List<Interview> interviews = interviewService.getAllInterviews().stream()
				.filter(i -> i.getStatus().toString().equals(interviewStatus.toUpperCase()))
				.collect(Collectors.toList());

		return new ResponseEntity<List<Interview>>(interviews, HttpStatus.OK);

	}

	@GetMapping("/interviews/{interviewId}")
	public ResponseEntity<Interview> getInterviewById(@PathVariable Long interviewId)
			throws InterviewNotFoundException {
		Interview interview = interviewService.getInterviewById(interviewId);
		return new ResponseEntity<Interview>(interview, HttpStatus.OK);
	}

	@GetMapping("/{interviewerId}/interviews/{interviewStatus}")
	public ResponseEntity<List<Interview>> getAllInterviews(@PathVariable Long interviewerId,
			@PathVariable String interviewStatus) {

		List<Interview> interviews = interviewService.getInterviewsByInterviewerId(interviewerId).stream()
				.filter(i -> i.getStatus().toString().equals(interviewStatus.toUpperCase()))
				.collect(Collectors.toList());

		return new ResponseEntity<List<Interview>>(interviews, HttpStatus.OK);

	}

	@PostMapping("/interviews/schedule")
	public ResponseEntity<Interview> scheduleNewInterview(@RequestBody NewInterviewRequest interviewRequest)
			throws InterviewScheduleConflictException, CandidateNotFoundException, InterviewerNotFoundException {

		Interview interview = interviewService.scheduleInterview(interviewRequest);
		return new ResponseEntity<Interview>(interview, HttpStatus.CREATED);
	}

	@PutMapping("/interviews/update")
	public ResponseEntity<Interview> updateInterview(@RequestBody Interview interview) {
		Interview updatedInterview = interviewService.updateInterview(interview);
		return new ResponseEntity<Interview>(updatedInterview, HttpStatus.OK);
	}

	@DeleteMapping("/interviews/{interviewId}/delete")
	public ResponseEntity<String> deleteInterview(@PathVariable Long interviewId) {
		interviewService.deleteInterviewById(interviewId);
		String message = "Interview deleted sucessfully";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

}
