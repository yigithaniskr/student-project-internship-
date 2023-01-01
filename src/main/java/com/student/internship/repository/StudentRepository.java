package com.student.internship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.internship.model.City;
import com.student.internship.model.Student;
import com.student.internship.model.Town;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	List<Student> findByName(String name);
	List<Student> findBySurname(String surname);
	Student findByIdentityNumber(String identityNumber);
	Student findByPhoneNumber(String phoneNumber);
	List<Student> findByCity(City city);
	List<Student> findByTown(Town town);
}
