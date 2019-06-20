package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.OcenaDTO;
import com.ISAtim4.WebAppSpringAirport.service.AvioKompanijaService;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.LetService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RentACarService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;
import com.ISAtim4.WebAppSpringAirport.service.SobaService;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;

@RestController
public class OcenjivanjeController {
	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RezervacijaService rezervacijaService;

	@Autowired
	OcenaService ocenaService;
	
	@Autowired
	KorisnikService korisnikService;

	@Autowired
	AvioKompanijaService avioKompanijaService;

	@Autowired
	LetService letService;

	@Autowired
	RentACarService rentACarService;

	@Autowired
	VoziloService voziloService;

	@Autowired
	HotelService hotelService;

	@Autowired
	SobaService sobaService;
	

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/api/ocenjivanje", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Ocena>> rejectRequest(Principal user,
			@Valid @RequestBody List<OcenaDTO> ocene) {
		ArrayList<Ocena> kreiraneOcene=ocenaService.ocenjivanje(user, ocene);
		if(kreiraneOcene== null)
		{
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok().body(kreiraneOcene);
	}
}