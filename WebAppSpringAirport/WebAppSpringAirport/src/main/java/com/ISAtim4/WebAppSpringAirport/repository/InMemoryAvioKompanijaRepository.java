package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;

@Repository
public class InMemoryAvioKompanijaRepository {
	private static Long counter=(long) 0;
	private final ConcurrentMap<Long, AvioKompanija> avioKompanije = new ConcurrentHashMap<Long, AvioKompanija>();
	
	
	public Collection<AvioKompanija> findAll() {
		return this.avioKompanije.values();
	}

	
	public AvioKompanija create(AvioKompanija avioKompanija) {
		Long id=avioKompanija.getId();
		if(id==null) {
			id = ++counter;
			avioKompanija.setId(id);
		}
		this.avioKompanije.put(id, avioKompanija);
		return avioKompanija;
	}

	
	public AvioKompanija findOne(Long id) {
		return this.avioKompanije.get(id);
	}

	
	public AvioKompanija update(AvioKompanija avioKompanija) {
		Long id = avioKompanija.getId();
		if (id != null) {
			this.avioKompanije.put(id, avioKompanija);
		}
		return avioKompanija;
	}

	
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
