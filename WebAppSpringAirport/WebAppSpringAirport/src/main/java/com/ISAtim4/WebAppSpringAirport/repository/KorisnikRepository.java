package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

	Korisnik findByKorisnickoIme(String username);

	Korisnik findByKorisnickoImeAndLozinka(String username, String password);

	@Query("select k from Korisnik k where k.id = ?1")
	Korisnik findOneID(Long id);

	@Query("select k from Korisnik k where k.id not in ?1")
	List<Korisnik> findNotConnectedPeople(List<Long> ids);
	
	@Query("select k from Korisnik k where k.id in ?1")
	ArrayList<Korisnik> findKorisniciIds(List<Long> ids);
	
	@Query("select k from Korisnik k where k.id in ?1")
	Set<Korisnik> findKorisniciIdsSet(List<Long> ids);
}
