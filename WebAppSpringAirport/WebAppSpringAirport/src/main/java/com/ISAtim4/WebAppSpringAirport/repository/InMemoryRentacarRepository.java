package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.ISAtim4.WebAppSpringAirport.domain.RentACar;

@Repository
public class InMemoryRentacarRepository implements RentacarRepository{
	private static Long counter=(long) 0;
	private final ConcurrentMap<Long, RentACar> rentacars = new ConcurrentHashMap<Long, RentACar>();
	
	@Override
	public Collection<RentACar> findAll() {
		return this.rentacars.values();
	}

	@Override
	public RentACar create(RentACar rentacar) {
		Long id=rentacar.getId();
		if(id==null) {
			id = ++counter;
			rentacar.setId(id);
		}
		this.rentacars.put(id, rentacar);
		return rentacar;
	}

	@Override
	public RentACar findOne(Long id) {
		return this.rentacars.get(id);
	}

	@Override
	public RentACar update(RentACar rentacar) {
		Long id = rentacar.getId();
		if (id != null) {
			this.rentacars.put(id, rentacar);
		}
		return rentacar;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
