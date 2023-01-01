package com.student.internship.response;

import com.student.internship.model.Student;

import lombok.Data;

@Data
public class StudentResponse {
	
	private Long id;
	private String identityNumber;
	private String name;
	private String surname;
	private String phoneNumber;
	private String cityName;
	private String townName;
	private Long cityId;
	private Long townId;
	
	public StudentResponse(Student student) {
		this.id= student.getId();
		this.identityNumber = student.getIdentityNumber();
		this.name = student.getName();
		this.surname=  student.getSurname();
		this.phoneNumber = student.getPhoneNumber();
		this.cityName = student.getCity().getName();
		this.townName = student.getTown().getName();
		this.cityId = student.getCity().getId();
		this.townId = student.getTown().getId();
	}

}
