package com.plannerAce.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Candidate;
import com.plannerAce.Models.Feedback;
import com.plannerAce.Models.Interview;
import com.plannerAce.Models.Interviewer;
import com.plannerAce.Payload.Request.FeedbackRequest;
import com.plannerAce.Repositories.CandidateRepository;
import com.plannerAce.Repositories.FeedbackRepository;
import com.plannerAce.Repositories.InterviewRepository;
import com.plannerAce.Repositories.InterviewerRepository;



@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private InterviewRepository interviewRepository;

	@Autowired
	private InterviewerRepository interviewerRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	public Feedback addFeedback(Long InterviewId, Long interviewerId, FeedbackRequest feedbackRequest)
			throws InterviewNotFoundException, InterviewerNotFoundException, CandidateNotFoundException {
		Interview interview = interviewRepository.findById(InterviewId)
				.orElseThrow(() -> new InterviewNotFoundException("Interview not found"));

		Interviewer interviewer = interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new InterviewerNotFoundException("Interviewer not found"));

		Feedback feedback = new Feedback();
		feedback.setInterview(interview);
		feedback.setInterviewer(interviewer);
		feedback.setCandidate(interview.getCandidate());
		feedback.setComments(feedbackRequest.getComments());
		feedback.setRating(feedbackRequest.getRating());

		return feedbackRepository.save(feedback);
	}

	@Override
	public Feedback getFeedbackByInterviewId(Long interviewId) throws InterviewNotFoundException {
		Interview interview = interviewRepository.findById(interviewId)
				.orElseThrow(() -> new InterviewNotFoundException("Interview not found"));

		return feedbackRepository.findByInterview(interview);
	}

	@Override
	public List<Feedback> getFeedbackByInterviewerId(Long interviewerId) throws InterviewerNotFoundException {
		Interviewer interviewer = interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new InterviewerNotFoundException("Interviewer not found"));

		return feedbackRepository.findByInterviewer(interviewer);
	}

	@Override
	public List<Feedback> getFeedbackByCandidateId(Long candidateId) throws CandidateNotFoundException {
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new CandidateNotFoundException("Candidate not found"));

		return feedbackRepository.findByCandidate(candidate);
	}
}

