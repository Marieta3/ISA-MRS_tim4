package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Usluga;

public interface UslugaRepository extends JpaRepository<Usluga, Long>{
	List<Usluga> findAllByHotel(Hotel hotel);
}
