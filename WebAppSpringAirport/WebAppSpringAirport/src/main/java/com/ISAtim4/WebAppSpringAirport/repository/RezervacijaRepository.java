package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long>{
	//@Query(value="select r from Rezervacija r where ?1 in (select rk from Rezervacija.korisnici as rk)", nativeQuery=true)
	//@Query("select r from Rezervacija r where ?1 in r.korisnici")
	@Query("select r from Rezervacija r")
	List<Rezervacija> findAllByUser(RegistrovaniKorisnik rk);
	
}
