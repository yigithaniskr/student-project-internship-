package com.student.internship.service;

import org.springframework.stereotype.Service;

import com.student.internship.model.Town;
import com.student.internship.repository.TownRepository;

@Service
public class TownService {
	
	private final TownRepository townRepository;
	
	public TownService(TownRepository townRepository) {
		this.townRepository = townRepository;
	}
	
	public Town getOneTownByTownId(Long id) {
		return townRepository.findById(id).orElse(null);
	}
	
	public Town getOneTownByName(String name) {
		return townRepository.findByName(name);
	}

}
