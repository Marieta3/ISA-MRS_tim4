package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	List<Hotel> findAllByNaziv(String naziv);

	@Query("select h from Hotel h where h.naziv like %?1% ")
	List<Hotel> pronadjiHotelSadrziNaziv(String naziv);

	@Query("select h from Hotel h where h.adresa = ?1")
	List<Hotel> searchHotelsLocation(String lokacija, Date datumPolaska, Date datumDolaska);

	@Query("select h from Hotel h where h.naziv = ?1")
	List<Hotel> searchHotelsName(String nazivHotela, Date datumPolaska, Date datumDolaska);
	
	/*@Query("select h from Hotel h where h.naziv = ?1 and h not in (select r.soba.hotel from Rezervacija r where (r.sobaZauzetaOd <= ?2 and r.sobaZauzetaDo >= ?2) or (r.sobaZauzetaOd <= ?3 and r.sobaZauzetaDo >= ?3) or (r.sobaZauzetaOd >= ?2 and r.sobaZauzetaDo <= ?3))")
	List<Hotel> searchHotelsNameDate(String nazivHotela, Date datumPolaska, Date datumDolaska);
	
	@Query("select h from Hotel h where h.adresa = ?1 and h not in (select r.soba.hotel from Rezervacija r where (r.sobaZauzetaOd <= ?2 and r.sobaZauzetaDo >= ?2) or (r.sobaZauzetaOd <= ?3 and r.sobaZauzetaDo >= ?3) or (r.sobaZauzetaOd >= ?2 and r.sobaZauzetaDo <= ?3))")
	List<Hotel> searchHotelsLocationDate(String lokacija, Date vreme1, Date vreme2);
	
	@Query("select h from Hotel h where h.naziv = ?1 and h not in (select s.hotel from Rezervacija.odabraneSobe s )")
	List<Hotel> searchHotelsNameDate(String nazivHotela, Date datumPolaska, Date datumDolaska);*/
}
