package com.student.internship.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.student.internship.exception.IdentityNumberPresentException;
import com.student.internship.exception.PhoneNumberPresentException;
import com.student.internship.exception.StudentNotFoundException;
import com.student.internship.model.City;
import com.student.internship.model.Student;
import com.student.internship.model.Town;
import com.student.internship.repository.StudentRepository;
import com.student.internship.request.BaseStudentRequest;
import com.student.internship.response.StudentResponse;

@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
	private final CityService cityService;
	private final TownService townService;
	
	public StudentService(StudentRepository studentRepository, CityService cityService, TownService townService) {
		this.studentRepository = studentRepository;
		this.cityService = cityService;
		this.townService = townService;
	}
	
	public List<StudentResponse> getAllStudents(){
		List<Student> studentList = studentRepository.findAll();
		return studentListToStudentResponseList(studentList);
	}
	
	public StudentResponse createOneStudent(BaseStudentRequest studentCreateRequest) {
		City city = cityService.getOneCityByCityName(studentCreateRequest.getCityName());
		Town town = townService.getOneTownByName(studentCreateRequest.getTownName());
		Student studentIN = studentRepository.findByIdentityNumber(studentCreateRequest.getIdentityNumber());
		Student studentPN =  studentRepository.findByPhoneNumber(studentCreateRequest.getPhoneNumber());
		
		if(studentIN != null) {
			throw new IdentityNumberPresentException("Identity number is not unique.");
		}
		if(studentPN != null) {
			throw new PhoneNumberPresentException("Phone number is not unique.");
		}
		
		Student student = new Student();
		student.setName(studentCreateRequest.getName());
		student.setSurname(studentCreateRequest.getSurname());
		student.setIdentityNumber(studentCreateRequest.getIdentityNumber());
		student.setPhoneNumber(studentCreateRequest.getPhoneNumber());
		student.setCity(city);
		student.setTown(town);
		
		studentRepository.save(student);
		StudentResponse response  = new StudentResponse(student);
		return response;
	}
	
	public List<StudentResponse> getStudentsByName(String name){
		List<Student> studentList = studentRepository.findByName(name);
		checkStudentList(studentList);
		return studentListToStudentResponseList(studentList);
	}
	
	public List<StudentResponse> getStudentsByCityName(String name){
		City city = cityService.getOneCityByCityName(name);
		List<Student> studentList = studentRepository.findByCity(city);
		checkStudentList(studentList);
		return studentListToStudentResponseList(studentList);
	}
	
	public List<StudentResponse> getStudentsByTownName(String name){
		Town town  = townService.getOneTownByName(name);
		List<Student> studentList =  studentRepository.findByTown(town);
		checkStudentList(studentList);
		return studentListToStudentResponseList(studentList);
	}
	
	public List<StudentResponse> getStudentsBySurname(String surname){
		List<Student> studentList = studentRepository.findBySurname(surname);
		checkStudentList(studentList);
		return studentListToStudentResponseList(studentList);
	}
	
	public StudentResponse getStudentByIdentityNumber(String identityNumber) {
		Student student = studentRepository.findByIdentityNumber(identityNumber);
		checkStudent(student);
		return new StudentResponse(student);
	}
	
	public StudentResponse getStudentByPhoneNumber(String phoneNumber) {
		Student student = studentRepository.findByPhoneNumber(phoneNumber);
		checkStudent(student);
		return new StudentResponse(student);
	}
	
	public StudentResponse updateOneStudent(Long id, BaseStudentRequest studentUpdateRequest) {
		Optional<Student> student = studentRepository.findById(id);
		City city = cityService.getOneCityByCityName(studentUpdateRequest.getCityName());
		Town town = townService.getOneTownByName(studentUpdateRequest.getTownName());
		Student studentIN = studentRepository.findByIdentityNumber(studentUpdateRequest.getIdentityNumber());
		Student studentPN = studentRepository.findByPhoneNumber(studentUpdateRequest.getPhoneNumber());
		
		if(student.isPresent()) {
			Student studentToSave = student.get();
			if(studentIN != null) {
				if(student.get().getIdentityNumber() == studentIN.getIdentityNumber()) {
					student.get().setIdentityNumber(studentUpdateRequest.getIdentityNumber());
				}
				else{
					throw new IdentityNumberPresentException("Identity number is not unique.");
				}
			}
			else {
				student.get().setIdentityNumber(studentUpdateRequest.getIdentityNumber());
			}
			
			if(studentPN != null) {
				if(student.get().getPhoneNumber() == studentPN.getPhoneNumber()) {
					student.get().setPhoneNumber(studentUpdateRequest.getPhoneNumber());
				}
				else {
					throw new PhoneNumberPresentException("Phone number is not unique.");
				}
			}
			else {
				student.get().setPhoneNumber(studentUpdateRequest.getPhoneNumber());
			}
			studentToSave.setName(studentUpdateRequest.getName());
			studentToSave.setSurname(studentUpdateRequest.getSurname());
			studentToSave.setCity(city);
			studentToSave.setTown(town);
			studentRepository.save(studentToSave);
			return new StudentResponse(studentToSave);
		}else {
			throw new StudentNotFoundException("Student not found!");
		}
			
	}
	
	public void deleteOneStudent(Long id) {
		studentRepository.deleteById(id);
	}
	
	private void checkStudent(Student student) {
		if(student == null) {
			throw new StudentNotFoundException("Student not found!");
		}
	}
	private void checkStudentList(List<Student> students) {
		if(students.size()<=0) {
			throw new StudentNotFoundException("Student not found!");
		}
	}
	
	public List<StudentResponse> studentListToStudentResponseList(List<Student> students) {
		return students.stream().map(student -> new StudentResponse(student)).collect(Collectors.toList());
	}
	
}
