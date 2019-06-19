package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;

public interface PozivnicaRepository extends JpaRepository<Pozivnica, Long> {
	@Query("select p from Pozivnica p where p.komeSalje = ?1 and p.reagovanoNaPoziv = false")
	ArrayList<Pozivnica> findMyInvitations(Korisnik k);
}
