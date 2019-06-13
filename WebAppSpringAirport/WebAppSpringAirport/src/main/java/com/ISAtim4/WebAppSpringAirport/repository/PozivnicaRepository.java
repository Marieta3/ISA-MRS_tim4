package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;

public interface PozivnicaRepository extends JpaRepository<Pozivnica, Long> {
	@Query("select p from Pozivnica p where p.komeSalje = ?1")
	ArrayList<Pozivnica> findMyInvitations(Korisnik k);
}
