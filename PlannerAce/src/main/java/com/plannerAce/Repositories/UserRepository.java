package com.plannerAce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plannerAce.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
