package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.BrzaSoba;
import com.ISAtim4.WebAppSpringAirport.repository.BrzaSobaRepository;
@Service
@Transactional(readOnly = true)
public class BrzaSobaService {
	@Autowired
	private BrzaSobaRepository brzaSobaRepository;
	
	@Transactional(readOnly = false)
	public BrzaSoba save(BrzaSoba brzaSoba) {
		return brzaSobaRepository.save(brzaSoba);
	}

	public BrzaSoba findOne(Long id) {
		return brzaSobaRepository.getOne(id);
	}

	public List<BrzaSoba> findAll() {
		return brzaSobaRepository.findAll();
	}

	public Page<BrzaSoba> findAll(Pageable page) {
		return brzaSobaRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		brzaSobaRepository.deleteById(id);
	}
	
}
