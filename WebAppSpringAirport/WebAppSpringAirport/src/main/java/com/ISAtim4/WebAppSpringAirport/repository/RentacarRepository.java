package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;


import com.ISAtim4.WebAppSpringAirport.domain.RentACar;


public interface RentacarRepository {
	Collection<RentACar> findAll();

	RentACar create(RentACar rentACar);

	RentACar findOne(Long id);
	
	RentACar update(RentACar hotel);

	void delete(Long id);

}
