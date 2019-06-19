package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.AdminHotel;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Usluga;
import com.ISAtim4.WebAppSpringAirport.dto.UslugaDTO;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.UslugaService;

@RestController
public class UslugaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UslugaService uslugaService;
	
	@Autowired
	HotelService hotelService;
	
	@Autowired
	KorisnikService korisnikService;
	/* da snimimo uslugu */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@PostMapping(value = "/api/usluge",  produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Usluga createUsluga(@Valid @RequestBody UslugaDTO uslugaDTO) {
		Usluga usluga=new Usluga();
		usluga.setCena(uslugaDTO.getCena());
		usluga.setOpis(uslugaDTO.getOpis());
		Hotel hotel=hotelService.findOne(uslugaDTO.getHotel_id());
		usluga.setHotel(hotel);
		return uslugaService.save(usluga);
	}

	/* da uzmemo sve usluge, svima dozvoljeno */
	@GetMapping(value = "/api/usluge",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Usluga> getAllUsluge() {
		return uslugaService.findAll();
	}
	
	@GetMapping(value = "/api/uslugeHotela/{hotel_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Usluga> getUslugeByHotel(@PathVariable(value = "hotel_id") Long hotel_id) {
		Hotel hotel=hotelService.findOne(hotel_id);
		return uslugaService.findAllByHotel(hotel);
	}
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@GetMapping(value = "/api/uslugeHotela", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Usluga> getUslugeHotela(Principal user) {
		AdminHotel me=new AdminHotel();
		if(user!=null) {
			me=(AdminHotel) korisnikService.findByKorisnickoIme(user.getName());
		}
		return uslugaService.findAllByHotel(me.getHotel());
	}
	/* da uzmemo uslugu po id-u, svima dozvoljeno */
	@GetMapping(value = "/api/usluge/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usluga> getUsluga(
			@PathVariable(value = "id") Long uslugaId) {
		Usluga usluga = uslugaService.findOne(uslugaId);

		if (usluga == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(usluga);
	}

	/* update usluge po id-u */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@PutMapping(value = "/api/usluge/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usluga> updateUsluge(
			@PathVariable(value = "id") Long uslugaId,
			@Valid @RequestBody Usluga uslugaDetalji) {

		Usluga usluga = uslugaService.findOne(uslugaId);
		if (usluga == null) {
			return ResponseEntity.notFound().build();
		}

		usluga.setCena(uslugaDetalji.getCena());
		usluga.setOpis(uslugaDetalji.getOpis());

		Usluga updateUsluga = uslugaService.save(usluga);
		return ResponseEntity.ok().body(updateUsluga);
	}

	/* brisanje usluge */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@DeleteMapping(value = "/api/usluge/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usluga> deleteUsluga(
			@PathVariable(value = "id") Long uslugaId) {
		Usluga usluga = uslugaService.findOne(uslugaId);

		if (usluga != null) {
			uslugaService.remove(uslugaId);
			logger.info("Usluga " + uslugaId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Usluga " + uslugaId + " not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
