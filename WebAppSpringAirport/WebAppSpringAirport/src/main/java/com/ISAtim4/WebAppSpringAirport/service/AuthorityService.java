package com.ISAtim4.WebAppSpringAirport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Authority;
import com.ISAtim4.WebAppSpringAirport.repository.AuthorityRepository;

@Service
@Transactional(readOnly = true)
public class AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public Authority findByName(String name){
		return authorityRepository.findByName(name);
	}
}
