package com.plannerAce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plannerAce.Models.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
