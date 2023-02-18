package com.plannerAce.Services;

import java.util.List;

import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Interviewer;

public interface InterviewerService {
	List<Interviewer> getAllInterviewers();

	Interviewer getInterviewerById(Long interviewerId) throws InterviewerNotFoundException;

	Interviewer saveInterviewer(Interviewer interviewer);

	Interviewer updateInterviewer(Long interviewerId, Interviewer updatedInterviewer)
			throws InterviewerNotFoundException;

	void deleteInterviewer(Long interviewerId) throws InterviewerNotFoundException;
}

