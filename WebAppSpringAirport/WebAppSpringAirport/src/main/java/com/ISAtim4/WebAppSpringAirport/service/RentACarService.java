package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.repository.RentacarRepository;

@Service
@Transactional(readOnly = true)
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

	@Transactional(readOnly = false)
	public RentACar save(RentACar rentACar) {
		return rentacarRepository.save(rentACar);
	}
	
	public List<RentACar> findByName(String name){
		return rentacarRepository.findAllByNaziv(name);
	}

	public List<RentACar> containsName(String name){
		return rentacarRepository.pronadjiRentacarSadrziNaziv(name);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		rentacarRepository.deleteById(id);
	}

	public List<RentACar> searchRentsLocation(String lokNaziv, Date datumPolaska, Date datumDolaska) {
		return rentacarRepository.searchRentsLocation(lokNaziv,datumPolaska,datumDolaska);
	}

	public List<RentACar> searchRentsName(String lokNaziv, Date datumPolaska, Date datumDolaska) {
		return rentacarRepository.searchRentsName(lokNaziv,datumPolaska,datumDolaska);
	}
}
