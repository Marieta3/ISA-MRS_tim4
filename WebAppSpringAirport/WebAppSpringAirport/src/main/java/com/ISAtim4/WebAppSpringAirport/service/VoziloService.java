package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;

public interface VoziloService {
	Collection<Vozilo> findAll();

	Vozilo findOne(Long id);

	Vozilo create(Vozilo vozilo) throws Exception;

	Vozilo update(Vozilo vozilo) throws Exception;

	void delete(Long id);
}
