package com.student.internship.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String identityNumber;
	private String name;
	private String surname;
	@Column(unique = true)
	private String phoneNumber;
	
	@OneToOne
	@JoinColumn(name = "city_id")
	private City city;
	
	@OneToOne
	@JoinColumn(name = "town_id")
	private Town town;
	
	

}
