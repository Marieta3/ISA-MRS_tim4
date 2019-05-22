package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;


public interface SobaRepository extends JpaRepository<Soba, Long>{
	List<Soba> findAllByHotel(Hotel hotel);
	/*@Transactional
	@Modifying
	@Query("update Soba s set s.rezervisana = true where s.id in ?1")
	void updateReservedRooms(List<Long> ids);*/
	@Query("select s from Soba s where s.id in ?1")
	Set<Soba> findSobeIds(List<Long> ids);
}
