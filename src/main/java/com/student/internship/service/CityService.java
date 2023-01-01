package com.student.internship.service;

import org.springframework.stereotype.Service;

import com.student.internship.model.City;
import com.student.internship.repository.CityRepository;

@Service
public class CityService {
	
	private final CityRepository cityRepository;

	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	public City getOneCityByCityId(Long cityId) {
		return cityRepository.findById(cityId).orElse(null);
	}
	
	public City getOneCityByCityName(String cityName) {
		return cityRepository.findByName(cityName);
	}

}
