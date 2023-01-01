package com.student.internship.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.internship.request.BaseStudentRequest;
import com.student.internship.response.StudentResponse;
import com.student.internship.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping
	public ResponseEntity<List<StudentResponse>> getAllStudents(){
		return ResponseEntity.ok(studentService.getAllStudents());
	}
	
	@PostMapping
	public ResponseEntity<StudentResponse> createOneStudent(@Valid @RequestBody BaseStudentRequest studentCreateRequest){
		return ResponseEntity.ok(studentService.createOneStudent(studentCreateRequest));
	}
	
	@GetMapping("/name/{studentName}")
	public ResponseEntity<List<StudentResponse>> getStudentsByName(@PathVariable String studentName){
		return ResponseEntity.ok(studentService.getStudentsByName(studentName));
	}
	
	@GetMapping("/city/{cityName}")
	public ResponseEntity<List<StudentResponse>> getStudentsByCityName(@PathVariable String cityName){
		return ResponseEntity.ok(studentService.getStudentsByCityName(cityName));
	}
	
	@GetMapping("/town/{townName}")
	public ResponseEntity<List<StudentResponse>> getStudentsByTownName(@PathVariable String townName){
		return ResponseEntity.ok(studentService.getStudentsByTownName(townName));
	}
	
	@GetMapping("/surname/{studentSurname}")
	public ResponseEntity<List<StudentResponse>> getStudentsBySurname(@PathVariable String studentSurname){
		return ResponseEntity.ok(studentService.getStudentsBySurname(studentSurname));
	}
	
	@GetMapping("/identityNumber/{identityNumber}")
	public ResponseEntity<StudentResponse> getStudentByIdentityNumber(@PathVariable String identityNumber){
		return ResponseEntity.ok(studentService.getStudentByIdentityNumber(identityNumber));
	}
	
	@GetMapping("/phoneNumber/{phoneNumber}")
	public ResponseEntity<StudentResponse> getStudentByPhoneNumber(@PathVariable String phoneNumber){
		return ResponseEntity.ok(studentService.getStudentByPhoneNumber(phoneNumber));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<StudentResponse> updateOneStudent(@PathVariable Long id,@Valid  @RequestBody BaseStudentRequest studentUpdateRequest){
		return ResponseEntity.ok(studentService.updateOneStudent(id, studentUpdateRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOneStudent(@PathVariable Long id){
		studentService.deleteOneStudent(id);
		return ResponseEntity.ok().build();
		
	}

}
