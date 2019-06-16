package com.ISAtim4.WebAppSpringAirport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.repository.KorisnikRepository;

@Service
@Transactional(readOnly = true)
public class KorisnikService {
	@Autowired
	private KorisnikRepository korisnikRepository;

	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}

	public Korisnik findOne(Long id) {
		return korisnikRepository.getOne(id);
	}

	public Page<Korisnik> findAll(Pageable page) {
		return korisnikRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public Korisnik save(Korisnik korisnik) {
		return korisnikRepository.save(korisnik);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		korisnikRepository.deleteById(id);
	}

	public Korisnik findByKorisnickoImeAndLozinka(String username,
			String password) {
		return korisnikRepository.findByKorisnickoImeAndLozinka(username,
				password);
	}

	public Korisnik findByKorisnickoIme(String username) {
		return korisnikRepository.findByKorisnickoIme(username);
	}

	public Korisnik findOneID(Long id) {
		return korisnikRepository.findOneID(id);
	}
	
	public List<Korisnik>findNotConnectedPeople(List<Long>ids){
		return korisnikRepository.findNotConnectedPeople(ids);
	}
	
	public ArrayList<Korisnik> findAllIds(List<Long> ids){
		return korisnikRepository.findKorisniciIds(ids);
	}
	
	public Set<Korisnik> findAllIdsSet(List<Long> ids){
		return korisnikRepository.findKorisniciIdsSet(ids);
	}
}
