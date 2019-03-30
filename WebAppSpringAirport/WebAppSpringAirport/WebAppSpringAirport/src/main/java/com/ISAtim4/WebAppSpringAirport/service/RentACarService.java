package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.repository.RentacarRepository;

@Service
public class RentACarService {
	@Autowired
	private RentacarRepository rentacarRepository;
	
	public List<RentACar> findAll(){
		return rentacarRepository.findAll();
	}

	public RentACar findOne(Long id) {
		return rentacarRepository.getOne(id);
	}

	public Page<RentACar> findAll(Pageable page) {
		return rentacarRepository.findAll(page);
	}

	public RentACar save(RentACar rentACar) {
		return rentacarRepository.save(rentACar);
	}

	public void remove(Long id) {
		rentacarRepository.deleteById(id);
	}
}
