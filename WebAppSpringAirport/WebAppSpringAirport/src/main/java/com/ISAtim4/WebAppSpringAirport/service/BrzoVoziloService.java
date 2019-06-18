package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.BrzaSoba;
import com.ISAtim4.WebAppSpringAirport.domain.BrzoVozilo;
import com.ISAtim4.WebAppSpringAirport.repository.BrzaSobaRepository;
import com.ISAtim4.WebAppSpringAirport.repository.BrzoVoziloRepository;

@Service
@Transactional(readOnly = true)
public class BrzoVoziloService {
	@Autowired
	private BrzoVoziloRepository brzoVoziloRepository;
	
	@Transactional(readOnly = false)
	public BrzoVozilo save(BrzoVozilo brzoVozilo) {
		return brzoVoziloRepository.save(brzoVozilo);
	}

	public BrzoVozilo findOne(Long id) {
		return brzoVoziloRepository.getOne(id);
	}

	public List<BrzoVozilo> findAll() {
		return brzoVoziloRepository.findAll();
	}

	public Page<BrzoVozilo> findAll(Pageable page) {
		return brzoVoziloRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		brzoVoziloRepository.deleteById(id);
	}
}
