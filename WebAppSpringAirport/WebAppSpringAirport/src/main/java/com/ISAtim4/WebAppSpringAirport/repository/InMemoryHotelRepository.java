package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;

@Repository
public class InMemoryHotelRepository implements HotelRepository{
	private static Long counter=(long) 0;
	private final ConcurrentMap<Long, Hotel> hotels = new ConcurrentHashMap<Long, Hotel>();
	
	@Override
	public Collection<Hotel> findAll() {
		return this.hotels.values();
	}

	@Override
	public Hotel create(Hotel hotel) {
		Long id=hotel.getId();
		if(id==null) {
			id = ++counter;
			hotel.setId(id);
		}
		this.hotels.put(id, hotel);
		return hotel;
	}

	@Override
	public Hotel findOne(Long id) {
		return this.hotels.get(id);
	}

	@Override
	public Hotel update(Hotel hotel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
