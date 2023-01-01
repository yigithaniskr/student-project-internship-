package com.student.internship.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseStudentRequest {
	
	@NotBlank(message = "Identity Number must not be null")
	@Size(min=11, max=11, message="Identity Number length must be equal to 11")
	private String identityNumber;
	
	@NotBlank(message = "Name must not be null")
	private String name;
	
	@NotBlank(message = "Surname must not be null")
	private String surname;
	
	@NotBlank(message = "Phone Number must not be null")
	@Size(min=11, max=11, message="Phone Numberlength must be equal to 11")
	private String phoneNumber;
	
	@NotBlank(message = "City Name must not be null")
	private String cityName;
	
	@NotBlank(message = "Town Name must not be null")
	private String townName;
}
