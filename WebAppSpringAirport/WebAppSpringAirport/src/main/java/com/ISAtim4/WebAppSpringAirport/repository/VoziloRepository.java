package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;

import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;

public interface VoziloRepository {
	Collection<Vozilo> findAll();

	Vozilo create(Vozilo vozilo);

	Vozilo findOne(Long id);
	
	Vozilo update(Vozilo vozilo);

	void delete(Long id);
}
