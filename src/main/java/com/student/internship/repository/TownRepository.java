package com.student.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.internship.model.Town;

public interface TownRepository extends JpaRepository<Town, Long>{

	Town findByName(String name);

}
