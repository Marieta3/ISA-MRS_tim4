package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.BrzaSoba;

public interface BrzaSobaRepository extends JpaRepository<BrzaSoba, Long>{

	@Query("select bs from BrzaSoba bs where bs.soba.hotel.id = ?1 and bs.start_date = ?2 and bs.end_date = ?3 and bs.zauzeto = false")
	List<BrzaSoba> getBrzeSobeHotela(Long hotelId, Date vreme1, Date vreme2);
}
