package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Sediste;


public interface SedisteRepository extends JpaRepository<Sediste, Long>{

	@Query("select s from Sediste s where s.let.id = ?1 and s.row_col in ?2")
	Set<Sediste> findAllByLetRowCol(Long id_leta, List<String> row_col);
	
	@Query("select s from Sediste s where s.let.id = ?1 and s.row_col = ?2")
	Sediste findOneByLetRowCol(Long let_id, String row_col); 
}
