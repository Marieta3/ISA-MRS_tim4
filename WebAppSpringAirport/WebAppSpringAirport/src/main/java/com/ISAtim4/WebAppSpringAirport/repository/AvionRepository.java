package com.ISAtim4.WebAppSpringAirport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.Avion;

public interface AvionRepository extends JpaRepository<Avion, Long> {

}
