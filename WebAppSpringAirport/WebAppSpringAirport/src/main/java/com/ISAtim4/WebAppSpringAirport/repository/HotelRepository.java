package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	List<Hotel> findAllByNaziv(String naziv);

	@Query("select h from Hotel h where h.naziv like %?1% ")
	List<Hotel> pronadjiHotelSadrziNaziv(String naziv);

	List<Hotel> searchHotelsLocation();

	List<Hotel> searchHotelsName();
}
