package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;

@Repository
public class InMemoryKorisnikRepository implements KorisnikRepository {
	private static Long counter=(long) 0;
	private final ConcurrentMap<Long, Korisnik> korisnici = new ConcurrentHashMap<Long, Korisnik>();
	
	@Override
	public Collection<Korisnik> findAll() {
		return this.korisnici.values();
	}

	@Override
	public Korisnik create(Korisnik korisnik) {
		Long id=korisnik.getId();
		if(id==null) {
			id = ++counter;
			korisnik.setId(id);
		}
		this.korisnici.put(id, korisnik);
		return korisnik;
	}

	@Override
	public Korisnik findOne(Long id) {
		return this.korisnici.get(id);
	}

	@Override
	public Korisnik update(Korisnik korisnik) {
		Long id = korisnik.getId();
		if (id != null) {
			this.korisnici.put(id, korisnik);
		}
		return korisnik;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
