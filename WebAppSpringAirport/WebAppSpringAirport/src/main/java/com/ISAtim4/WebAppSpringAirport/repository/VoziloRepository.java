package com.ISAtim4.WebAppSpringAirport.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;

public interface VoziloRepository extends JpaRepository<Vozilo, Long>{
	/*@Transactional
	@Modifying
	@Query("update Vozilo v set v.rezervisano = true where v.id in ?1")
	void updateCarReservation(List<Long> ids);*/
	@Query("select v from Vozilo v where v.id in ?1")
	Set<Vozilo> findVozilaIds(List<Long> ids);
	
}
