package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;


import com.ISAtim4.WebAppSpringAirport.domain.Hotel;


public interface HotelRepository {
	Collection<Hotel> findAll();

	Hotel create(Hotel hotel);

	Hotel findOne(Long id);
	
	Hotel update(Hotel hotel);

	void delete(Long id);

}
