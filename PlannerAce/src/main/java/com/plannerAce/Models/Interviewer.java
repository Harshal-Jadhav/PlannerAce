package com.plannerAce.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "interviewers")
public class Interviewer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String phoneNumber;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "interviewer_availability", joinColumns = @JoinColumn(name = "interviewer_id"), inverseJoinColumns = @JoinColumn(name = "availability_id"))
	private Set<Availability> availabilities = new HashSet<>();

	@OneToMany(mappedBy = "interviewer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Interview> interviews = new ArrayList<>();

}
