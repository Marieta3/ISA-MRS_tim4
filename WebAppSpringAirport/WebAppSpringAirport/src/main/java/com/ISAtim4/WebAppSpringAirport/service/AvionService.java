package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Avion;
import com.ISAtim4.WebAppSpringAirport.repository.AvionRepository;

@Service
public class AvionService {

	@Autowired
	private AvionRepository avionRepository;

	public Avion save(Avion avion) {
		return avionRepository.save(avion);
	}

	public Avion findOne(Long id) {
		return avionRepository.getOne(id);
	}

	public List<Avion> findAll() {
		return avionRepository.findAll();
	}

	/*
	 * public List<Avion> findAllByRentACar(RentACar r) { return
	 * avionRepository.findAllByRentACar(r); }
	 */

	public Page<Avion> findAll(Pageable page) {
		return avionRepository.findAll(page);
	}

	public void remove(Long id) {
		avionRepository.deleteById(id);
	}
}
