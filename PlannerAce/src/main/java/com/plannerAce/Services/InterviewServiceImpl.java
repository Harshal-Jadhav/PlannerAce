package com.plannerAce.Services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewScheduleConflictException;
import com.plannerAce.Models.Candidate;
import com.plannerAce.Models.Interview;
import com.plannerAce.Models.Interviewer;
import com.plannerAce.Repositories.CandidateRepository;
import com.plannerAce.Repositories.InterviewRepository;
import com.plannerAce.Repositories.InterviewerRepository;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private InterviewRepository interviewRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private InterviewerRepository interviewerRepository;

	@Override
	public List<Interview> getAllInterviews() {
		return interviewRepository.findAll();
	}

	@Override
	public Interview getInterviewById(Long interviewId) throws InterviewNotFoundException {
		return interviewRepository.findById(interviewId).orElseThrow(()-> new InterviewNotFoundException("Interview not found with Id "+interviewId));
	}

	@Override
	public List<Interview> getInterviewsByCandidateId(Long candidateId) {
		Optional<Candidate> candidate = candidateRepository.findById(candidateId);
		return candidate.isPresent() ? interviewRepository.findByCandidate(candidate.get()) : Collections.emptyList();
	}

	@Override
	public List<Interview> getInterviewsByInterviewerId(Long interviewerId) {
		Optional<Interviewer> interviewer = interviewerRepository.findById(interviewerId);
		return interviewer.isPresent() ? interviewRepository.findByInterviewer(interviewer.get())
				: Collections.emptyList();
	}

	@Override
	public List<Interview> getInterviewsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
		return interviewRepository.findByStartTimeBetween(startDate, endDate);
	}

	@Override
	public Interview scheduleInterview(Interview interview) throws InterviewScheduleConflictException {
		LocalDateTime interviewStartTime = interview.getStartTime();
		LocalDateTime interviewEndTime = interview.getEndTime();

		List<Interview> conflictingInterviews = interviewRepository
				.findByInterviewerAndStartTimeBetween(interview.getInterviewer(), interviewStartTime, interviewEndTime);

		conflictingInterviews.addAll(interviewRepository.findByCandidateAndStartTimeBetween(interview.getCandidate(),
				interviewStartTime, interviewEndTime));

		if (conflictingInterviews.isEmpty())
			return interviewRepository.save(interview);
		else
			throw new InterviewScheduleConflictException("Interview schedule conflict detected");
	}


	@Override
	public Interview updateInterview(Interview interview) {
		return interviewRepository.save(interview);
	}

	@Override
	public void deleteInterviewById(Long interviewId) {
		interviewRepository.deleteById(interviewId);
	}
}
