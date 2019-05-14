package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Destinacija;
import com.ISAtim4.WebAppSpringAirport.repository.DestinacijaRepository;

@Service
public class DestinacijaService {
	@Autowired
	private DestinacijaRepository destinacijaRepository;
	
	public Destinacija save(Destinacija destinacija) {
		return destinacijaRepository.save(destinacija);
	}
	
	public Destinacija findOne(Long id) {
		return destinacijaRepository.getOne(id);
	}

	public List<Destinacija> findAll() {
		return destinacijaRepository.findAll();
	}
	
	public Page<Destinacija> findAll(Pageable page) {
		return destinacijaRepository.findAll(page);
	}
	
	//public List<Destinacija> findByName(String name){
	//	return destinacijaRepository.findAllByNaziv(name);
	//}

	//public List<Destinacija> containsName(String name){
	//	return destinacijaRepository.pronadjiAvioSadrziNaziv(name);
	//}

	public void remove(Long id) {
		destinacijaRepository.deleteById(id);
	}
}