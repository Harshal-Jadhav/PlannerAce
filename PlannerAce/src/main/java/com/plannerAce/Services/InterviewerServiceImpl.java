package com.plannerAce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Interviewer;
import com.plannerAce.Repositories.InterviewerRepository;

@Service
public class InterviewerServiceImpl implements InterviewerService {

	@Autowired
	private InterviewerRepository interviewerRepository;

	@Override
	public Interviewer saveInterviewer(Interviewer interviewer) {
		return interviewerRepository.save(interviewer);
	}

	@Override
	public List<Interviewer> getAllInterviewers() {
		return interviewerRepository.findAll();
	}

	@Override
	public Interviewer getInterviewerById(Long interviewerId) throws InterviewerNotFoundException {
		return interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new InterviewerNotFoundException("Interviewer not found with ID: " + interviewerId));
	}

	@Override
	public Interviewer updateInterviewer(Long interviewerId, Interviewer interviewerDetails)
			throws InterviewerNotFoundException {
		Interviewer interviewer = getInterviewerById(interviewerId);

		interviewer.setName(interviewerDetails.getName());
		interviewer.setEmail(interviewerDetails.getEmail());
		interviewer.setPhoneNumber(interviewerDetails.getPhoneNumber());
		interviewer.setPassword(interviewerDetails.getPassword());

		return interviewerRepository.save(interviewer);
	}

	@Override
	public void deleteInterviewer(Long interviewerId) {
		interviewerRepository.deleteById(interviewerId);
	}
}

