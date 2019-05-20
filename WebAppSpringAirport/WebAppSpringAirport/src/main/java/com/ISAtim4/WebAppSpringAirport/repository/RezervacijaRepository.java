package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long>{
	
}
