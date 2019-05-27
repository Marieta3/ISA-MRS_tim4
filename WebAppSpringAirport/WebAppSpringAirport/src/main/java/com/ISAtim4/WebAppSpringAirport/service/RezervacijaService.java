package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.repository.RezervacijaRepository;


@Service
public class RezervacijaService {
	@Autowired
	private RezervacijaRepository rezervacijaRepository;

	public Rezervacija save(Rezervacija hotel) {
		return rezervacijaRepository.save(hotel);
	}

	public Rezervacija findOne(Long id) {
		return rezervacijaRepository.getOne(id);
	}

	public List<Rezervacija> findAll() {
		return rezervacijaRepository.findAll();
	}

	public Page<Rezervacija> findAll(Pageable page) {
		return rezervacijaRepository.findAll(page);
	}
	
	public List<Rezervacija> findAllByUser(RegistrovaniKorisnik rk){
		return rezervacijaRepository.findAllByUser(rk);
	}
	

	public void remove(Long id) {
		rezervacijaRepository.deleteById(id);
	}
	
}
