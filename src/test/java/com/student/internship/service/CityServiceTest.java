package com.student.internship.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.student.internship.model.City;
import com.student.internship.repository.CityRepository;

public class CityServiceTest {

	private CityRepository cityRepository;
	private CityService service;
	
	@BeforeEach
	void setUp() {
		cityRepository =  mock(CityRepository.class);
		service = new CityService(cityRepository);
	}
	
	@Test
	public void testGetOneCityByCityId_whenCityIdExist_shouldReturnCity() {
		City city = new City("İstanbul");
		Mockito.when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
		City result = service.getOneCityByCityId(city.getId());
		assertEquals(result, city);
	}
	
	@Test
	public void testGetOneCityByCityId_whenCityNameExist_shouldReturnCity() {
		City city = new City("İstanbul");
		Mockito.when(cityRepository.findByName(city.getName())).thenReturn(city);
		City result = service.getOneCityByCityName("İstanbul");
		assertEquals(result, city);
	}
}
