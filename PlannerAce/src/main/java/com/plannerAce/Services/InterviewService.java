package com.plannerAce.Services;

import java.time.LocalDateTime;
import java.util.List;

import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewScheduleConflictException;
import com.plannerAce.Models.Interview;

public interface InterviewService {

	List<Interview> getAllInterviews();

	Interview getInterviewById(Long interviewId) throws InterviewNotFoundException;

	List<Interview> getInterviewsByCandidateId(Long candidateId);

	List<Interview> getInterviewsByInterviewerId(Long interviewerId);

	List<Interview> getInterviewsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

	Interview scheduleInterview(Interview interview) throws InterviewScheduleConflictException;

	Interview updateInterview(Interview interview);

	void deleteInterviewById(Long interviewId);
}

