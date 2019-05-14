package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Destinacija;


public interface DestinacijaRepository extends JpaRepository<Destinacija, Long> {
	//List<Destinacija> findAllByNaziv(String naziv);

	//@Query("select a from AvioKompanija a where a.naziv like %?1% ")
	//List<Destinacija> pronadjiAvioSadrziAdresa(String naziv);

}
