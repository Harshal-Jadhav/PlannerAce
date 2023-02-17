package com.plannerAce.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "feedbacks")
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "interview_id")
	private Interview interview;

	@ManyToOne
	@JoinColumn(name = "interviewer_id")
	private User interviewer;

	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private User candidate;

	private String comments;
	private int rating;
}
