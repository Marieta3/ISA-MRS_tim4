package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;

public interface KorisnikService {
	Collection<Korisnik> findAll();

	Korisnik findOne(Long id);

	Korisnik create(Korisnik korisnik) throws Exception;

	Korisnik update(Korisnik korisnik) throws Exception;

	void delete(Long id);
}
