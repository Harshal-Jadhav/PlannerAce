package com.plannerAce.Models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "feedbacks")
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "interview_id")
	private Interview interview;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "interviewer_id")
	private Interviewer interviewer;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	private String comments;
	private int rating;
}
