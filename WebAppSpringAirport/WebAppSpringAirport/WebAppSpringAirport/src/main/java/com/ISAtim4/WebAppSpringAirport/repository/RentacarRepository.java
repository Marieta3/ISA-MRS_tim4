package com.ISAtim4.WebAppSpringAirport.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.RentACar;


public interface RentacarRepository extends JpaRepository<RentACar, Long>{
	
    List<RentACar> findAllByNaziv(String naziv);
    
    @Query("select r from RentACar r where r.naziv like %?1% " )
	List<RentACar> pronadjiRentacarSadrziNaziv(String naziv);
    
    
}
