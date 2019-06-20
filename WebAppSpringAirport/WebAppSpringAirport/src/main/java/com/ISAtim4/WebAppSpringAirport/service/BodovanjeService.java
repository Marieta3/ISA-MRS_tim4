package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Bodovanje;
import com.ISAtim4.WebAppSpringAirport.repository.BodovanjeRepository;

@Service
public class BodovanjeService {
	@Autowired
	private BodovanjeRepository bodovanjeRepository;


	public Bodovanje save(Bodovanje b) {
		return bodovanjeRepository.save(b);
	}

	public Bodovanje findOne(Long id) {
		return bodovanjeRepository.getOne(id);
	}

	public List<Bodovanje> findAll() {
		return bodovanjeRepository.findAll();
	}

	public Page<Bodovanje> findAll(Pageable page) {
		return bodovanjeRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		bodovanjeRepository.deleteById(id);
	}
}
