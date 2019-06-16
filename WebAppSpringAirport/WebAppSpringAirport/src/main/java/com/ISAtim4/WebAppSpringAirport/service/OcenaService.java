package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.repository.OcenaRepository;

@Service
@Transactional(readOnly = true)
public class OcenaService {
	@Autowired
	private OcenaRepository ocenaRepository;

	@Transactional(readOnly = false)
	public Ocena save(Ocena ocena) {
		return ocenaRepository.save(ocena);
	}

	public Ocena findOne(Long id) {
		return ocenaRepository.getOne(id);
	}

	public List<Ocena> findAll() {
		return ocenaRepository.findAll();
	}
	
	public List<Ocena> findAllByRezervacija(Rezervacija r) {
		return ocenaRepository.findAllByRezervacija(r);
	}
	
	public List<Ocena> findAllByRent(RentACar r) {
		return ocenaRepository.findAllByRent(r);
	}
	public List<Ocena> findAllByVozilo(Vozilo v) {
		return ocenaRepository.findAllByVozilo(v);
	}
	public List<Ocena> findAllByLet(Let l) {
		return ocenaRepository.findAllByLet(l);
	}
	public List<Ocena> findAllByAvio(AvioKompanija a) {
		return ocenaRepository.findAllByAvio(a);
	}
	public List<Ocena> findAllByHotel(Hotel h) {
		return ocenaRepository.findAllByHotel(h);
	}
	public List<Ocena> findAllBySoba(Soba s) {
		return ocenaRepository.findAllBySoba(s);
	}

	public Page<Ocena> findAll(Pageable page) {
		return ocenaRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		ocenaRepository.deleteById(id);
	}
}
