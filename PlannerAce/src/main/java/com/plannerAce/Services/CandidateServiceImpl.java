package com.plannerAce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plannerAce.Exceptions.CandidateNotFoundException;
import com.plannerAce.Models.Candidate;
import com.plannerAce.Repositories.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	public Candidate createCandidate(Candidate candidate) {
		return candidateRepository.save(candidate);
	}

	@Override
	public Candidate getCandidateById(Long id) throws CandidateNotFoundException {
		return candidateRepository.findById(id)
				.orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id : " + id));
	}

	@Override
	public List<Candidate> getAllCandidates() {
		return candidateRepository.findAll();
	}

	@Override
	public void updateCandidate(Long id, Candidate candidate) throws CandidateNotFoundException {
		if (!candidateRepository.existsById(id))
			throw new CandidateNotFoundException("Candidate not found with id : " + id);
		candidate.setId(id);
		candidateRepository.save(candidate);
	}

	@Override
	public void deleteCandidate(Long id) throws CandidateNotFoundException {
		if (!candidateRepository.existsById(id))
			throw new CandidateNotFoundException("Candidate not found with id : " + id);
		candidateRepository.deleteById(id);
	}
}

