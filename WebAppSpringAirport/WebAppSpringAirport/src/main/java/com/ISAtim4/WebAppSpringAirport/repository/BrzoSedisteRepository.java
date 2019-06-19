package com.ISAtim4.WebAppSpringAirport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.BrzoSediste;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;

public interface BrzoSedisteRepository extends JpaRepository<BrzoSediste, Long>{
	BrzoSediste findOneBySediste(Sediste sediste);
}
