package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Usluga;
import com.ISAtim4.WebAppSpringAirport.repository.UslugaRepository;

@Service
public class UslugaService {
	@Autowired
	private UslugaRepository uslugaRepository;
	
	public Usluga save(Usluga usluga) {
		return uslugaRepository.save(usluga);
	}
	
	public Usluga findOne(Long id) {
		return uslugaRepository.getOne(id);
	}

	public List<Usluga> findAll() {
		return uslugaRepository.findAll();
	}
	
	public Page<Usluga> findAll(Pageable page) {
		return uslugaRepository.findAll(page);
	}

	public void remove(Long id) {
		uslugaRepository.deleteById(id);
	}
}
