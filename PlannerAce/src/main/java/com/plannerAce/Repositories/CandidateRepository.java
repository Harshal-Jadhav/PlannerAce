package com.plannerAce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plannerAce.Models.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}
