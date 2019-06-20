package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public RentACar update(Long rentAcarId, RentACar rentAcarDetalji) {
		RentACar rentACar = findOne(rentAcarId);
		if (rentACar == null) {
			return null;
		}
		rentACar.setNaziv(rentAcarDetalji.getNaziv());
		rentACar.setAdresa(rentAcarDetalji.getAdresa());
		rentACar.setOpis(rentAcarDetalji.getOpis());
		//rentACar.setSlika(rentAcarDetalji.getSlika());
		rentACar.setCoord1(rentAcarDetalji.getCoord1());
		rentACar.setCoord2(rentAcarDetalji.getCoord2());
		RentACar updateRentACar = save(rentACar);
		return updateRentACar;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean delete(Long rentAcarId) {
		RentACar rentACar = findOne(rentAcarId);
		if (rentACar != null) {
			remove(rentAcarId);
			return true;
		} else {
			return false;
		}
	}
}
