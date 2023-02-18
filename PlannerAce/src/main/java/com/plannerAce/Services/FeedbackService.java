package com.plannerAce.Services;

import java.util.List;

import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Feedback;

public interface FeedbackService {
	Feedback addFeedback(Long interviewId, Long interviewerId, Long candidateId, String comments, int rating)
			throws InterviewNotFoundException, InterviewerNotFoundException, CandidateNotFoundException;

	List<Feedback> getFeedbackByInterviewId(Long interviewId) throws InterviewNotFoundException;

	List<Feedback> getFeedbackByInterviewerId(Long interviewerId) throws InterviewerNotFoundException;

	List<Feedback> getFeedbackByCandidateId(Long candidateId) throws CandidateNotFoundException;
}

