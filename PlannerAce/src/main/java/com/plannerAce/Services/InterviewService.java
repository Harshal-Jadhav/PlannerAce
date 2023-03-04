package com.plannerAce.Services;

import java.time.LocalDateTime;
import java.util.List;

import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewScheduleConflictException;
import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Interview;
import com.plannerAce.Payload.Request.NewInterviewRequest;

public interface InterviewService {

	List<Interview> getAllInterviews();

	Interview getInterviewById(Long interviewId) throws InterviewNotFoundException;

	List<Interview> getInterviewsByCandidateId(Long candidateId);

	List<Interview> getInterviewsByInterviewerId(Long interviewerId);

	List<Interview> getInterviewsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

	Interview scheduleInterview(NewInterviewRequest newInterviewRequest)
			throws InterviewScheduleConflictException, CandidateNotFoundException, InterviewerNotFoundException;

	Interview updateInterview(Interview interview);

	void deleteInterviewById(Long interviewId);
}

