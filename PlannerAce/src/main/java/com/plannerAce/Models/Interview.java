package com.plannerAce.Models;

import java.time.LocalDateTime;

import com.plannerAce.Enums.InterviewStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "interviews")
public class Interview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String location;
	private String comments;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InterviewStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interviewer_id")
	private Interviewer interviewer;


	@PrePersist
	@PreUpdate
	private void checkAvailability() {
		if (candidate != null && interviewer != null) {
			boolean isAvailable = candidate.getInterviews().stream()
					.noneMatch(i -> i.getStartTime().equals(startTime) || i.getEndTime().equals(endTime));
			if (!isAvailable)
				throw new RuntimeException("Candidate is not available at the scheduled time");

			isAvailable = interviewer.getInterviews().stream()
					.noneMatch(i -> i.getStartTime().equals(startTime) || i.getEndTime().equals(endTime));
			if (!isAvailable)
				throw new RuntimeException("Interviewer is not available at the scheduled time");
		}
	}
}
