package com.ISAtim4.WebAppSpringAirport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.repository.SedisteRepository;

@Service
@Transactional(readOnly = true)
public class SedisteService {
	@Autowired
	private SedisteRepository sedisteRepository;

	@Transactional(readOnly = false)
	public Sediste save(Sediste sediste) {
		return sedisteRepository.save(sediste);
	}

	public Sediste findOne(Long id) {
		return sedisteRepository.getOne(id);
	}

	public List<Sediste> findAll() {
		return sedisteRepository.findAll();
	}

	public Page<Sediste> findAll(Pageable page) {
		return sedisteRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		sedisteRepository.deleteById(id);
	}

	public Set<Sediste> findAllByLetRowCol(Long id_leta, List<String> row_col){
		return sedisteRepository.findAllByLetRowCol(id_leta, row_col);
	}
	
}
