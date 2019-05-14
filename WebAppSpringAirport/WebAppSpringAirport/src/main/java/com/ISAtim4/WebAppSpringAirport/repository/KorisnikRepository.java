package com.ISAtim4.WebAppSpringAirport.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{

	Korisnik findByKorisnickoIme(String username);
	
	Korisnik findByKorisnickoImeAndLozinka(String username, String password);
	
	@Query("select k from Korisnik k where k.id = ?1")
	Korisnik findOneID(Long id);
}
