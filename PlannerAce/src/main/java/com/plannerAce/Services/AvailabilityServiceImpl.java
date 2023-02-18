package com.plannerAce.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plannerAce.Exceptions.ResourceNotFoundException;
import com.plannerAce.Models.Availability;
import com.plannerAce.Models.Interviewer;
import com.plannerAce.Repositories.AvailabilityRepository;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Override
	public List<Availability> getAllAvailabilities() {
		return availabilityRepository.findAll();
	}

	@Override
	public Availability getAvailabilityById(Long id) throws ResourceNotFoundException {
		return availabilityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Availability id " + id));
	}

	@Override
	public Availability saveAvailability(Availability availability) {
		return availabilityRepository.save(availability);
	}

	@Override
	public void deleteAvailability(Long id) throws ResourceNotFoundException {
		Availability availability = getAvailabilityById(id);
		availabilityRepository.delete(availability);
	}

	@Override
	public List<Availability> getAvailabilitiesByInterviewer(Interviewer interviewer) {
		return availabilityRepository.findByInterviewers(interviewer);
	}

	@Override
	public List<Availability> getAvailableAvailabilities(LocalDateTime start, LocalDateTime end) {
		return availabilityRepository.findAvailableAvailabilities(start, end);
	}
}

