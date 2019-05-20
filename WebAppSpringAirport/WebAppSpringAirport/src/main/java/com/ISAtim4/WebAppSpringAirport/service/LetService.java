package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.repository.LetRepository;

@Service
public class LetService {

	@Autowired
	private LetRepository letRepository;

	public Let save(Let let) {
		return letRepository.save(let);
	}

	public Let findOne(Long id) {
		return letRepository.getOne(id);
	}

	public List<Let> findAll() {
		return letRepository.findAll();
	}

	public Page<Let> findAll(Pageable page) {
		return letRepository.findAll(page);
	}

	public void remove(Long id) {
		letRepository.deleteById(id);
	}
	
	public List<Let> findFlightsOneWay(String mestoPolaska, String mestoDolaska) {
		return letRepository.findFlightsOneWay(mestoPolaska,mestoDolaska);
	}
	
	public List<Let> findFlightsTwoWay(String mestoPolaska, String mestoDolaska, Date vremePolaska, Date vremeDolaska){
		return letRepository.findFlightsTwoWay(mestoPolaska,mestoDolaska,vremePolaska,vremeDolaska);
	}


}
