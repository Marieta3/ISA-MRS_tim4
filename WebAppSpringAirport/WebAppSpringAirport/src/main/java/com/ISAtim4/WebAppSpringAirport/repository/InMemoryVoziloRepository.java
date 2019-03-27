package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;

@Repository
public class InMemoryVoziloRepository implements VoziloRepository{
	private static Long counter=(long) 0;
	private final ConcurrentMap<Long, Vozilo> vozila = new ConcurrentHashMap<Long, Vozilo>();

	@Override
	public Collection<Vozilo> findAll() {
		return this.vozila.values();
	}

	@Override
	public Vozilo create(Vozilo vozilo) {
		Long id=vozilo.getId();
		if(id==null) {
			id = ++counter;
			vozilo.setId(id);
		}
		this.vozila.put(id, vozilo);
		return vozilo;
	}

	@Override
	public Vozilo findOne(Long id) {
		return this.vozila.get(id);
	}

	@Override
	public Vozilo update(Vozilo vozilo) {
		Long id = vozilo.getId();
		if (id != null) {
			this.vozila.put(id, vozilo);
		}
		return vozilo;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
