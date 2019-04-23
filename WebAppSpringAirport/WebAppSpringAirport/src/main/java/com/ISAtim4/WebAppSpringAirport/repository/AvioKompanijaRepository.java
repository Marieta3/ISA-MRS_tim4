package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;

public interface AvioKompanijaRepository extends JpaRepository<AvioKompanija, Long> {
	List<AvioKompanija> findAllByNaziv(String naziv);

	@Query("select a from AvioKompanija a where a.naziv like %?1% ")
	List<AvioKompanija> pronadjiAvioSadrziNaziv(String naziv);

}
