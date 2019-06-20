package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.BrzaSoba;
import com.ISAtim4.WebAppSpringAirport.domain.BrzoVozilo;

public interface BrzoVoziloRepository extends JpaRepository<BrzoVozilo, Long>{
	
	@Query("select bs from BrzoVozilo bs where bs.vozilo.filijala.rentACar.id = ?1 and bs.start_date = ?2 and bs.end_date = ?3 and bs.zauzeto = false")
	List<BrzoVozilo> getBrzaVozila(Long hotelId, Date vreme1, Date vreme2);
}
