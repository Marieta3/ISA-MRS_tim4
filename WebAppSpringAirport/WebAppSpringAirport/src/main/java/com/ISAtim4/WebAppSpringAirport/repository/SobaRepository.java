package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;


public interface SobaRepository extends JpaRepository<Soba, Long>{
	List<Soba> findAllByHotel(Long id);
}
