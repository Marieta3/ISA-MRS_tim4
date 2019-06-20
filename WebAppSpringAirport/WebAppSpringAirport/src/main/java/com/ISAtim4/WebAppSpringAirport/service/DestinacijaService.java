package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Destinacija;
import com.ISAtim4.WebAppSpringAirport.repository.DestinacijaRepository;

@Service
@Transactional(readOnly = true)
public class DestinacijaService {
	@Autowired
	private DestinacijaRepository destinacijaRepository;

	@Transactional(readOnly = false)
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

	// public List<Destinacija> findByName(String name){
	// return destinacijaRepository.findAllByNaziv(name);
	// }

	// public List<Destinacija> containsName(String name){
	// return destinacijaRepository.pronadjiAvioSadrziNaziv(name);
	// }

	@Transactional(readOnly = false)
	public void remove(Long id) {
		destinacijaRepository.deleteById(id);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Destinacija update(Long destinacijaId,Destinacija destinacijaDetalji) {
		Destinacija destinacija = findOne(destinacijaId);
		if (destinacija == null) {
			return null;
		}

		destinacija.setAdresa(destinacijaDetalji.getAdresa());
		destinacija.setSlika(destinacijaDetalji.getSlika());
		Destinacija updateDestinacije = save(destinacija);
		return updateDestinacije;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean delete( Long destinacijaId) {
		Destinacija destinacija = findOne(destinacijaId);

		if (destinacija != null) {
			remove(destinacijaId);
			return true;
		} else {
			return false;
		}
	}
}
