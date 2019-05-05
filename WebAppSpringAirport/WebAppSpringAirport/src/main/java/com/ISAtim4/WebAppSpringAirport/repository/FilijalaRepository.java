package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.Filijala;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;

;

public interface FilijalaRepository extends JpaRepository<Filijala, Long> {

	
	List<Filijala> findAllByRentACar(RentACar r);

}
