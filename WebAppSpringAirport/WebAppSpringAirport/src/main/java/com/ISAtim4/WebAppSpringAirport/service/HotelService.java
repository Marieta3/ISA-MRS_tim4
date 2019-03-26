package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;


public interface HotelService {
	Collection<Hotel> findAll();

	Hotel findOne(Long id);

	Hotel create(Hotel hotel) throws Exception;

	Hotel update(Hotel hotel) throws Exception;

	void delete(Long id);
}
