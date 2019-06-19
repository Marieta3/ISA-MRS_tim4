package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.repository.LetRepository;

@Service
@Transactional(readOnly = true)
public class LetService {

	@Autowired
	private LetRepository letRepository;

	@Transactional(readOnly = false)
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

	@Transactional(readOnly = false)
	public void remove(Long id) {
		letRepository.deleteById(id);
	}
	
	public List<Let> findFlightsOneWay(String mestoPolaska, String mestoDolaska, Date vreme1, Date vreme2) {
		return letRepository.findFlightsOneWay(mestoPolaska,mestoDolaska, vreme1, vreme2);
	}
	public List<Let> findFlightsPolazakDolazak(String mestoPolaska, String mestoDolaska) {
		return letRepository.findFlightsPolazakDolazak(mestoPolaska,mestoDolaska);
	}
	public List<Let> findFlightsTwoWay(String mestoPolaska, String mestoDolaska, Date vremePolaska, Date vremeDolaska){
		return letRepository.findFlightsTwoWay(mestoPolaska,mestoDolaska,vremePolaska,vremeDolaska);
	}
	
	public List<Let> findFlightsByAvio(String mestoPolaska, String mestoDolaska, Date vremePolaska, Date vremeDolaska, Long idAvio)
	{
		return letRepository.findFlightsByAvio(mestoPolaska, mestoDolaska, vremePolaska, vremeDolaska, idAvio);
	}
	
	public List<Let> findAllByAvioKompanija(AvioKompanija avio_kompanija){
		return letRepository.findAllByAvioKompanija(avio_kompanija);
	}
}
