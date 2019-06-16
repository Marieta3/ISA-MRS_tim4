package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.repository.AvioKompanijaRepository;
@Service
@Transactional(readOnly = true)
public class AvioKompanijaService {
	@Autowired
	private AvioKompanijaRepository avioKompanijaRepository;
	
	@Transactional(readOnly = false)
	public AvioKompanija save(AvioKompanija avioKompanija) {
		return avioKompanijaRepository.save(avioKompanija);
	}
	
	public AvioKompanija findOne(Long id) {
		return avioKompanijaRepository.getOne(id);
	}

	public List<AvioKompanija> findAll() {
		return avioKompanijaRepository.findAll();
	}
	
	public Page<AvioKompanija> findAll(Pageable page) {
		return avioKompanijaRepository.findAll(page);
	}
	
	public List<AvioKompanija> findByName(String name){
		return avioKompanijaRepository.findAllByNaziv(name);
	}

	public List<AvioKompanija> containsName(String name){
		return avioKompanijaRepository.pronadjiAvioSadrziNaziv(name);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		avioKompanijaRepository.deleteById(id);
	}
}
