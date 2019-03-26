package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import com.ISAtim4.WebAppSpringAirport.domain.RentACar;


public interface RentACarService {
	Collection<RentACar> findAll();

	RentACar findOne(Long id);

	RentACar create(RentACar rentACar) throws Exception;

	RentACar update(RentACar rentACar) throws Exception;

	void delete(Long id);
}
