package com.plannerAce.Services;

import java.util.List;

import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Feedback;
import com.plannerAce.Payload.Request.FeedbackRequest;

public interface FeedbackService {
	Feedback addFeedback(Long InterviewId, Long interviewerId, FeedbackRequest feedbackRequest)
			throws InterviewNotFoundException, InterviewerNotFoundException, CandidateNotFoundException;

	Feedback getFeedbackByInterviewId(Long interviewId) throws InterviewNotFoundException;

	List<Feedback> getFeedbackByInterviewerId(Long interviewerId) throws InterviewerNotFoundException;

	List<Feedback> getFeedbackByCandidateId(Long candidateId) throws CandidateNotFoundException;
}

