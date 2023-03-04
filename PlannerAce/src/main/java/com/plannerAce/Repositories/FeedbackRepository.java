package com.plannerAce.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plannerAce.Models.Candidate;
import com.plannerAce.Models.Feedback;
import com.plannerAce.Models.Interview;
import com.plannerAce.Models.Interviewer;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	Feedback findByInterview(Interview interview);

	List<Feedback> findByInterviewer(Interviewer interviewer);

	List<Feedback> findByCandidate(Candidate candidate);

}
