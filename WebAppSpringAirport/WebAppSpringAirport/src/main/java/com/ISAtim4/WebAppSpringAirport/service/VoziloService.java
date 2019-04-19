package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.repository.VoziloRepository;


@Service
public class VoziloService{
	@Autowired
	private VoziloRepository voziloRepository;
	
	public List<Vozilo> findAll(){
		return voziloRepository.findAll();
	}

	public Vozilo findOne(Long id) {
		return voziloRepository.getOne(id);
	}

	public Page<Vozilo> findAll(Pageable page) {
		return voziloRepository.findAll(page);
	}

	public Vozilo save(Vozilo vozilo) {
		return voziloRepository.save(vozilo);
	}

	public void remove(Long id) {
		voziloRepository.deleteById(id);
	}
}
