package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Filijala;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.repository.FilijalaRepository;

@Service
public class FilijalaService {
	@Autowired
	private FilijalaRepository branchRepository;

	public Filijala save(Filijala branch) {
		return branchRepository.save(branch);
	}

	public Filijala findOne(Long id) {
		return branchRepository.getOne(id);
	}

	public List<Filijala> findAll() {
		return branchRepository.findAll();
	}
	
	public List<Filijala> findAllByRentACar(RentACar r) {
		return branchRepository.findAllByRentACar(r);
	}

	public Page<Filijala> findAll(Pageable page) {
		return branchRepository.findAll(page);
	}

	public void remove(Long id) {
		branchRepository.deleteById(id);
	}
}
