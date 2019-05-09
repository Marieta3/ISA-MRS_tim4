package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Usluga;

public interface UslugaRepository extends JpaRepository<Usluga, Long>{
	List<Usluga> findAllByHotel(Hotel hotel);
	
	@Query("select u from Usluga u where u.id in ?1")
	List<Usluga> findAllSelected(List<Long> ids);
}
