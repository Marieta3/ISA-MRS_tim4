package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.BrzoSediste;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.repository.BrzoSedisteRepository;

@Service
@Transactional(readOnly = true)
public class BrzoSedisteService {
	@Autowired
	private BrzoSedisteRepository brzoSedisteRepository;
	
	@Transactional(readOnly = false)
	public BrzoSediste save(BrzoSediste brzoSediste) {
		return brzoSedisteRepository.save(brzoSediste);
	}

	public BrzoSediste findOne(Long id) {
		return brzoSedisteRepository.getOne(id);
	}

	public List<BrzoSediste> findAll() {
		return brzoSedisteRepository.findAll();
	}

	public Page<BrzoSediste> findAll(Pageable page) {
		return brzoSedisteRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		brzoSedisteRepository.deleteById(id);
	}
	
	public BrzoSediste findOneBySediste(Sediste sediste) {
		return brzoSedisteRepository.findOneBySediste(sediste);
	}
}
