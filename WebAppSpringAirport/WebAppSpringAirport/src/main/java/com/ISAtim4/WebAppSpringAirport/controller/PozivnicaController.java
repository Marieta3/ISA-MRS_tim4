package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.PozivnicaService;

@RestController
public class PozivnicaController {
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private PozivnicaService pozivnicaService;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/api/myInvitations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Pozivnica> getPozivnice(Principal user){
		Korisnik me = this.korisnikService.findByKorisnickoIme(user.getName());
		return pozivnicaService.findMyInvitations(me);
	}
	
}
