package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	
	/*
	public List<Rezervacija> findByName(String name) {
		return rezervacijaRepository.findAllByNaziv(name);
	}
	
	
	public List<Rezervacija> containsName(String name) {
		return rezervacijaRepository.pronadjiHotelSadrziNaziv(name);
	}*/

	public void remove(Long id) {
		rezervacijaRepository.deleteById(id);
	}
	/*
	public List<Rezervacija> searchHotelsLocation(String lokacija, Date datumPolaska, Date datumDolaska){
		return rezervacijaRepository.searchHotelsLocation(lokacija,datumPolaska,datumDolaska);
	}
	
	public List<Rezervacija> searchHotelsName(String nazivHotela, Date datumPolaska, Date datumDolaska){
		return rezervacijaRepository.searchHotelsName(nazivHotela,datumPolaska,datumDolaska);
	}*/
}
