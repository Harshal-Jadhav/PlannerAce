package com.plannerAce.Services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.plannerAce.Enums.InterviewStatus;
import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Exceptions.InterviewNotFoundException;
import com.plannerAce.Exceptions.InterviewScheduleConflictException;
import com.plannerAce.Exceptions.InterviewerNotFoundException;
import com.plannerAce.Models.Candidate;
import com.plannerAce.Models.Interview;
import com.plannerAce.Models.Interviewer;
import com.plannerAce.Payload.Request.NewInterviewRequest;
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

	@Autowired
	private JavaMailSender javaMailSender;

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
	public Interview scheduleInterview(NewInterviewRequest interviewRequest)
			throws InterviewScheduleConflictException, CandidateNotFoundException, InterviewerNotFoundException {

		Interview interview =  new Interview();
		interview.setComments(interviewRequest.getComments());
		interview.setStartTime(interviewRequest.getStartTime());
		interview.setEndTime(interviewRequest.getEndTime());
		interview.setLocation(interviewRequest.getLocation());
		interview.setStatus(InterviewStatus.SCHEDULED);

		Candidate candidate = candidateRepository.findById(interviewRequest.getCandidiateId())
				.orElseThrow(() -> new CandidateNotFoundException(
						"Candidate not found with Id " + interviewRequest.getCandidiateId()));

		Interviewer interviewer = interviewerRepository.findById(interviewRequest.getInterviewerId())
				.orElseThrow(() -> new InterviewerNotFoundException(
						"Interviewer Not found with Id " + interviewRequest.getInterviewerId()));

		interview.setCandidate(candidate);
		interview.setInterviewer(interviewer);

		LocalDateTime interviewStartTime = interview.getStartTime();
		LocalDateTime interviewEndTime = interview.getEndTime();

		List<Interview> conflictingInterviews = interviewRepository
				.findByInterviewerAndStartTimeBetween(interview.getInterviewer(), interviewStartTime, interviewEndTime);

		conflictingInterviews.addAll(interviewRepository.findByCandidateAndStartTimeBetween(interview.getCandidate(),
				interviewStartTime, interviewEndTime));

		if (!conflictingInterviews.isEmpty())
			throw new InterviewScheduleConflictException("Interview schedule conflict detected");


		sendMail(candidate, interviewer, interview);

		return interviewRepository.save(interview);
	}

	public void sendMail(Candidate candidate, Interviewer interviewer, Interview interview) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("harshaljadhav6444@gmail.com");
		mailMessage.setTo(candidate.getEmail());
		mailMessage.setCc(interviewer.getEmail());
		String Subject = "Congrats! Interview Scheduled";
		String message = "Hello " + candidate.getName() + ",\n"
				+ "We hope this message finds you well.\n"
				+ "We are happy to let you know that your interview is been scheduled.\n"
				+ "Please find the interview details below\n"
				+ "StartTime:- "+interview.getStartTime()+".\n"
				+ "EndTime:- " + interview.getEndTime() + ".\n"
				+ "Location:- "+interview.getLocation()+".\n"
				+ "Addidtional info:- " + interview.getComments()+".\n"
				+ "\n\n" + ""
				+ "Regards,\n"
				+ "Team PlannerAce.";

		mailMessage.setSubject(Subject);
		mailMessage.setText(message);

		javaMailSender.send(mailMessage);
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
