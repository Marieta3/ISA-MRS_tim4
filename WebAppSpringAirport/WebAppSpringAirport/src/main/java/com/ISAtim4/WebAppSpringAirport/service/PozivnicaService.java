package com.ISAtim4.WebAppSpringAirport.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;
import com.ISAtim4.WebAppSpringAirport.repository.PozivnicaRepository;

@Service
public class PozivnicaService {
	@Autowired
	private PozivnicaRepository pozivnicaRepository; 
	
	public Pozivnica save(Pozivnica pozivnica) {
		return pozivnicaRepository.save(pozivnica);
	}

	public Pozivnica findOne(Long id) {
		return pozivnicaRepository.getOne(id);
	}

	public List<Pozivnica> findAll() {
		return pozivnicaRepository.findAll();
	}

	public Page<Pozivnica> findAll(Pageable page) {
		return pozivnicaRepository.findAll(page);
	}

	public void remove(Long id) {
		pozivnicaRepository.deleteById(id);
	}
	
	public ArrayList<Pozivnica> findMyInvitations(Korisnik k){
		return pozivnicaRepository.findMyInvitations(k);
	}
}
