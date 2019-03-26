package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;


import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;

public interface KorisnikRepository {
	Collection<Korisnik> findAll();

	Korisnik create(Korisnik korisnik);

	Korisnik findOne(Long id);
	
	Korisnik update(Korisnik korisnik);

	void delete(Long id);
}
