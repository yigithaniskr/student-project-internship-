package com.student.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.internship.model.City;

public interface CityRepository extends JpaRepository<City, Long>{

	City findByName(String name);

}
