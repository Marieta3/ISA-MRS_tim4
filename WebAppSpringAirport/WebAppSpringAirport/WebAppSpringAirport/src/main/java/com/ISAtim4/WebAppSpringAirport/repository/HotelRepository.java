package com.ISAtim4.WebAppSpringAirport.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
}
