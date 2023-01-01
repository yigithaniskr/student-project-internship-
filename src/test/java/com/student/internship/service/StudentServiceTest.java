package com.student.internship.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.student.internship.exception.IdentityNumberPresentException;
import com.student.internship.exception.PhoneNumberPresentException;
import com.student.internship.exception.StudentNotFoundException;
import com.student.internship.model.City;
import com.student.internship.model.Student;
import com.student.internship.model.Town;
import com.student.internship.repository.StudentRepository;
import com.student.internship.request.BaseStudentRequest;
import com.student.internship.response.StudentResponse;


public class StudentServiceTest {
	private StudentRepository studentRepository;
	private CityService cityService;
	private TownService townService;
	private StudentService service;
	
	@BeforeEach
	void setUp() {
		studentRepository = mock(StudentRepository.class);
		cityService = mock(CityService.class);
		townService = mock(TownService.class);
		service =  new StudentService(studentRepository, cityService, townService);
	}
	
	@Test
	public void testGetStudentByIdentityNumber_whenIdentityNumberExist_shouldReturnStudentResponse() {
		
		Student student = createStudent();
		StudentResponse response = new StudentResponse(student);
		
		Mockito.when(studentRepository.findByIdentityNumber(student.getIdentityNumber())).thenReturn(student);
		StudentResponse result  = service.getStudentByIdentityNumber(response.getIdentityNumber());
		
		assertEquals(result, response);
	}
	
	@Test 
	public void testGetStudentByIdentityNumber_whenIdentityNumberDoesNotExist_shoulReturnStudentNotFoundException() {
		Mockito.when(studentRepository.findByIdentityNumber("23254985235")).thenReturn(null);
		
		assertThrows(StudentNotFoundException.class,
				() -> service.getStudentByIdentityNumber("23254985235"));
	}
	
	@Test
	public void testGetAllStudents_shouldReturnStudentResponseList() {
		List<Student> students = new ArrayList<>();
		Student student  = createStudent();
		students.add(student);
		List<StudentResponse> studentResponseList = students.stream()
				.map(s -> new StudentResponse(s)).collect(Collectors.toList());
		
		Mockito.when(studentRepository.findAll()).thenReturn(students);
		List<StudentResponse> result = service.getAllStudents();
		
		assertIterableEquals(result, studentResponseList);
	}
	
	@Test
	public void testGetStudentsByName_whenNameExist_shouldReturnStudentResponseList() {
		List<Student> students = createStudentList();
		List<StudentResponse> studentResponseList = students.stream()
				.map(s -> new StudentResponse(s)).collect(Collectors.toList());
		
		Mockito.when(studentRepository.findByName("deneme")).thenReturn(students);
		
		List<StudentResponse> result = service.getStudentsByName("deneme");
		assertIterableEquals(result, studentResponseList);
	}
	
	@Test
	public void testGetStudentsByName_whenNameDoesNotExist_shoulReturnStudentNotFoundException() {
		Mockito.when(studentRepository.findByName("deneme")).thenReturn(new ArrayList<>());
		assertThrows(StudentNotFoundException.class,
				() ->service.getStudentsByName("deneme"));
	}
	
	
	@Test
	public void testGetStudentsBySurname_whenSurnameExist_shouldReturnStudentResponseList() {
		List<Student> students = createStudentList();
		List<StudentResponse> studentResponseList = students.stream()
				.map(s -> new StudentResponse(s)).collect(Collectors.toList());
		
		Mockito.when(studentRepository.findBySurname("deneme")).thenReturn(students);
		
		List<StudentResponse> result = service.getStudentsBySurname("deneme");
		assertIterableEquals(result, studentResponseList);
	}
	
	@Test
	public void testGetStudentsBySurname_whenSurnameDoesNotExist_shoulReturnStudentNotFoundException() {
		Mockito.when(studentRepository.findBySurname("deneme")).thenReturn(new ArrayList<>());
		assertThrows(StudentNotFoundException.class,
				() ->service.getStudentsBySurname("deneme"));
	}
	
	@Test 
	public void testGetStudentByPhoneNumber_whenPhoneNumberExist_shouldReturnStudentResponse() {
		Student student = createStudent();
		StudentResponse response = new StudentResponse(student);
		
		Mockito.when(studentRepository.findByPhoneNumber(student.getPhoneNumber())).thenReturn(student);
		
		StudentResponse result = service.getStudentByPhoneNumber(response.getPhoneNumber());
		assertEquals(result, response);
	}
	
	@Test
	public void testGetStudentByPhoneNumber_whenSurnameDoesNotExist_shoulReturnStudentNotFoundException() {
		Mockito.when(studentRepository.findByPhoneNumber("05352126983")).thenReturn(null);
		assertThrows(StudentNotFoundException.class,
				() ->service.getStudentByPhoneNumber("05352126983"));
	}
	
	@Test 
	public void testGetStudentsByCityName_whenCityExist_shouldReturnStudentResponseList() {
		Student student = createStudent();
		List<Student> students = new ArrayList<>();
		students.add(student);
		List<StudentResponse> studentResponseList = students.stream()
				.map(s -> new StudentResponse(s)).collect(Collectors.toList());
		City city = student.getCity();
		Mockito.when(cityService.getOneCityByCityName(student.getCity().getName())).thenReturn(city);
		Mockito.when(studentRepository.findByCity(student.getCity())).thenReturn(students);
		
		List<StudentResponse> result = service.getStudentsByCityName(student.getCity().getName());
		assertIterableEquals(result, studentResponseList);
	}
	
	@Test
	public void testGetStudentsByCityName_whenCityDoesNotExist_shoulReturnStudentNotFoundException() {
		City city = new City(3L,"Adıyaman");
		Mockito.when(studentRepository.findByCity(city)).thenReturn(new ArrayList<>());
		assertThrows(StudentNotFoundException.class,
				() -> service.getStudentsByCityName("Adıyaman"));
	}
	
	@Test 
	public void testGetStudentsByTownName_whenTownExist_shouldReturnStudentResponseList() {
		Student student = createStudent();
		List<Student> students = new ArrayList<>();
		students.add(student);
		List<StudentResponse> studentResponseList = students.stream()
				.map(s -> new StudentResponse(s)).collect(Collectors.toList());
		Town town = student.getTown();
		Mockito.when(townService.getOneTownByName(student.getTown().getName())).thenReturn(town);
		Mockito.when(studentRepository.findByTown(student.getTown())).thenReturn(students);
		
		List<StudentResponse> result = service.getStudentsByTownName(student.getTown().getName());
		assertIterableEquals(result, studentResponseList);
	}
	
	@Test
	public void testCreateOneStudentCalledWithValidRequest_shouldReturnStudentResponse() {
		Student student = createStudent();
		City city = student.getCity();
		Town town = student.getTown();
		StudentResponse response = new StudentResponse(student); 
		BaseStudentRequest baseStudentRequest = new BaseStudentRequest(student.getIdentityNumber()
				,student.getName(), student.getSurname(), student.getPhoneNumber()
				, student.getCity().getName(), student.getTown().getName());
		
		Mockito.when(cityService.getOneCityByCityName(student.getCity().getName())).thenReturn(city);
		Mockito.when(townService.getOneTownByName(student.getTown().getName())).thenReturn(town);
		Mockito.when(studentRepository.save(student)).thenReturn(student);
		
		StudentResponse result = service.createOneStudent(baseStudentRequest);
		assertEquals(result, response);
		
	}
	
	@Test
	public void testCreateOneStudentCalledWithNotUniqueIdentityNumber_shouldReturnIdentityNumberPresentException() {
		Student student = createStudent();
		BaseStudentRequest baseStudentRequest = new BaseStudentRequest("23254985235","A","A","053158521459","İstanbul","Bakırköy");
		Mockito.when(studentRepository.findByIdentityNumber(baseStudentRequest.getIdentityNumber())).thenReturn(student);
		assertThrows(IdentityNumberPresentException.class,
				() -> service.createOneStudent(baseStudentRequest));
	}
	
	@Test
	public void testCreateOneStudentCalledWithNotUniquePhoneNumber_shouldReturnPhoneNumberPresentException() {
		Student student = createStudent();
		BaseStudentRequest baseStudentRequest = new BaseStudentRequest("23254985255","A","A","05352126987","İstanbul","Bakırköy");
		Mockito.when(studentRepository.findByPhoneNumber(baseStudentRequest.getPhoneNumber())).thenReturn(student);
		assertThrows(PhoneNumberPresentException.class,
				() -> service.createOneStudent(baseStudentRequest));
	}
	
	@Test
	public void testUpdateOneStudent_whenStudentExist_shouldReturnStudentResponse() {
		Student student  = createStudent();
		student.setId(1L);
		City city = student.getCity();
		Town town = student.getTown();
		BaseStudentRequest updateRequest = new BaseStudentRequest(student.getIdentityNumber(),student.getName()
				,student.getSurname(),student.getPhoneNumber(),student.getCity().getName(),student.getTown().getName());
		StudentResponse response = new StudentResponse(student);
		Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
		Mockito.when(studentRepository.findByIdentityNumber(student.getIdentityNumber())).thenReturn(null);
		Mockito.when(studentRepository.findByPhoneNumber(student.getPhoneNumber())).thenReturn(null);
		Mockito.when(cityService.getOneCityByCityName(student.getCity().getName())).thenReturn(city);
		Mockito.when(townService.getOneTownByName(student.getTown().getName())).thenReturn(town);
		Mockito.when(studentRepository.save(student)).thenReturn(student);
		StudentResponse result = service.updateOneStudent(1L, updateRequest);
		assertEquals(result, response);
	}
	@Test
	public void testUpdateOneStudentCalledWithSameStudentIdentityNumberAndPhoneNumber_shouldReturnStudentResponse() {
		Student student  = createStudent();
		student.setId(1L);
		City city = student.getCity();
		Town town = student.getTown();
		BaseStudentRequest updateRequest = new BaseStudentRequest(student.getIdentityNumber(),student.getName()
				,student.getSurname(),student.getPhoneNumber(),student.getCity().getName(),student.getTown().getName());
		StudentResponse response = new StudentResponse(student);
		Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
		Mockito.when(studentRepository.findByIdentityNumber(student.getIdentityNumber())).thenReturn(student);
		Mockito.when(studentRepository.findByPhoneNumber(student.getPhoneNumber())).thenReturn(student);
		Mockito.when(cityService.getOneCityByCityName(student.getCity().getName())).thenReturn(city);
		Mockito.when(townService.getOneTownByName(student.getTown().getName())).thenReturn(town);
		Mockito.when(studentRepository.save(student)).thenReturn(student);
		StudentResponse result = service.updateOneStudent(1L, updateRequest);
		assertEquals(result, response);

	}
	@Test 
	public void testUpdateOneStudentCalledWithNotUniqueIdentityNumber_shouldReturnIdentityNumberPresentException() {
		Student student = createStudent();
		student.setId(1L);
		
		City city = new City(1L,"İstanbul");
		Town town = new Town(1L,"Bakırköy",city);
		BaseStudentRequest baseStudentRequest = new BaseStudentRequest("12547854875","A","A","05352126987","İstanbul","Bakırköy");
		Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
		Student student2  = new Student(15L,"12547854875","s","s","05322120970",city,town);
		Mockito.when(studentRepository.findByIdentityNumber(baseStudentRequest.getIdentityNumber())).thenReturn(student2);
		assertThrows(IdentityNumberPresentException.class,
				() -> service.updateOneStudent(student.getId(), baseStudentRequest));
	}
	@Test 
	public void testUpdateOneStudentCalledWithNotUniquePhoneNumber_shouldReturnPhoneNumberPresentException() {
		Student student = createStudent();
		student.setId(1L);
		City city = new City(1L,"İstanbul");
		Town town = new Town(1L,"Bakırköy",city);
		BaseStudentRequest baseStudentRequest = new BaseStudentRequest("12547854875","A","A","05352224148","İstanbul","Bakırköy");
		Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
		Student student2  = new Student(15L,"15477752148","s","s","05352224148",city,town);
		Mockito.when(studentRepository.findByPhoneNumber(baseStudentRequest.getPhoneNumber())).thenReturn(student2);
		assertThrows(PhoneNumberPresentException.class,
				() -> service.updateOneStudent(student.getId(), baseStudentRequest));
	}
	
	@Test
	public void testUpdateOneStudent_whenStudentDoesNotExist_shoulReturnStudentNotFoundException() {
		
		BaseStudentRequest baseStudentRequest = new BaseStudentRequest("12547854875","A","A","05352126987","İstanbul","Bakırköy");
		
		Mockito.when(studentRepository.findById(22L)).thenReturn(Optional.empty());
		assertThrows(StudentNotFoundException.class,
				() -> service.updateOneStudent(1L,baseStudentRequest));
	}
	
	private Student createStudent() {
		Student student = new Student();
		City city = new City(1L,"İstanbul");
		Town town = new Town(1L,"Bakırköy",city);
		student.setIdentityNumber("23254985235");
		student.setName("deneme");
		student.setSurname("deneme");
		student.setPhoneNumber("05352126987");
		student.setCity(city);
		student.setTown(town);
		return student;
	}
	
	private List<Student> createStudentList(){
		List<Student> students = new ArrayList<>();
		Student student = createStudent();
		students.add(student);
		return students;
	}
}
