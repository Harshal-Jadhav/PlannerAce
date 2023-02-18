package com.plannerAce.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plannerAce.Models.Availability;
import com.plannerAce.Models.Interviewer;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

	List<Availability> findByInterviewers(Interviewer interviewer);

	List<Availability> findAvailableAvailabilities(LocalDateTime start, LocalDateTime end);

}
