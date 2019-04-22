package com.ISAtim4.WebAppSpringAirport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,Long>{

	Authority findByName(String name);

}
