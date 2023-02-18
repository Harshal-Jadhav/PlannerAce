package com.plannerAce.Services;

import java.time.LocalDateTime;
import java.util.List;

import com.plannerAce.Exceptions.ResourceNotFoundException;
import com.plannerAce.Models.Availability;
import com.plannerAce.Models.Interviewer;

public interface AvailabilityService {
	List<Availability> getAllAvailabilities();

	Availability getAvailabilityById(Long id) throws ResourceNotFoundException;

	Availability saveAvailability(Availability availability);

	void deleteAvailability(Long id) throws ResourceNotFoundException;

	List<Availability> getAvailabilitiesByInterviewer(Interviewer interviewer);

	List<Availability> getAvailableAvailabilities(LocalDateTime start, LocalDateTime end);
}

