package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Let;

public interface LetRepository extends JpaRepository<Let, Long>{
	
	@Query("select l from Let l where l.pocetnaDestinacija = ?1 and l.krajnjaDestinacija = ?2")
	public List<Let> findFlightsOneWay(String mestoPolaska, String mestoDolaska);

	@Query("select l from Let l where l.pocetnaDestinacija = ?1 and l.krajnjaDestinacija = ?2")
	public List<Let> findFlightsTwoWay(String mestoPolaska, String mestoDolaska, Date vremePolaska, Date vremeDolaska);
}
