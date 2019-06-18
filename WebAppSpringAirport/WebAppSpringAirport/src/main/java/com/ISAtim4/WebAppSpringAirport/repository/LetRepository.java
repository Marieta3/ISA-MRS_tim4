package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;

public interface LetRepository extends JpaRepository<Let, Long>{
	
	@Query("select l from Let l where l.pocetnaDestinacija = ?1 and l.krajnjaDestinacija = ?2 and l.vremePolaska >= ?3 and l.vremePolaska <= ?4")
	public List<Let> findFlightsOneWay(String mestoPolaska, String mestoDolaska, Date vreme1, Date vreme2);

	@Query("select l from Let l where l.pocetnaDestinacija = ?1 and l.krajnjaDestinacija = ?2")
	public List<Let> findFlightsTwoWay(String mestoPolaska, String mestoDolaska, Date vremePolaska, Date vremeDolaska);
	
	@Query("select l from Let l where l.pocetnaDestinacija = ?1 and l.krajnjaDestinacija = ?2 and l.vremePolaska >= ?3 and l.vremePolaska <= ?4 and l.avioKompanija.id = ?5")
	public List<Let> findFlightsByAvio(String mestoPolaska, String mestoDolaska, Date vremePolaska, Date vremeDolaska, Long idAvio);
	
	@Query("select l from Let l where l.pocetnaDestinacija = ?1 and l.krajnjaDestinacija = ?2")
	public List<Let> findFlightsPolazakDolazak(String mestoPolaska, String mestoDolaska);
	
	public List<Let> findAllByAvioKompanija(AvioKompanija avio_kompanija);
}
