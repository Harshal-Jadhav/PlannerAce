package com.plannerAce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plannerAce.Models.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

}
