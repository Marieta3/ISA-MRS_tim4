package com.ISAtim4.WebAppSpringAirport.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;



public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{
	
}
