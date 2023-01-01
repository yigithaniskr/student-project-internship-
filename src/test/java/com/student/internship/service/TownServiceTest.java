package com.student.internship.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.student.internship.model.City;
import com.student.internship.model.Town;
import com.student.internship.repository.TownRepository;

public class TownServiceTest {
	
	private TownRepository townRepository;
	private TownService townService;

	@BeforeEach
	void setUp() {
		townRepository = mock(TownRepository.class);
		townService = new TownService(townRepository);
	}
	
	@Test
	public void testGetOneTownByTownId_whenTownIdExist_shouldReturnTown() {
		City city = new City("İstanbul");
		Town town  = new Town("Bakırköy",city);
		Mockito.when(townRepository.findById(town.getId())).thenReturn(Optional.of(town));
		Town result = townService.getOneTownByTownId(town.getId());
		assertEquals(result, town);
	}
	
	@Test
	public void testGetOneTownByName_whenTownNameExist_shouldReturnTown() {
		City city = new City("İstanbul");
		Town town  = new Town("Bakırköy",city);
		Mockito.when(townRepository.findByName(town.getName())).thenReturn(town);
		Town result = townService.getOneTownByName(town.getName());
		assertEquals(result, town);
	}

}
