package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Usluga;
import com.ISAtim4.WebAppSpringAirport.service.UslugaService;

@RestController
public class UslugaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UslugaService uslugaService;

	/* da snimimo uslugu */
	@RequestMapping(value = "/api/usluge", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Usluga createUsluga(@Valid @RequestBody Usluga usluga) {
		System.out.println("Usooooooo");
		
		return uslugaService.save(usluga);
	}

	/* da uzmemo sve usluge */
	@RequestMapping(value = "/api/usluge", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Usluga> getAllUsluge() {
		return uslugaService.findAll();
	}

	/* da uzmemo uslugu po id-u */
	@RequestMapping(value = "/api/usluge/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usluga> getUsluga(
			@PathVariable(value = "id") Long uslugaId) {
		Usluga usluga = uslugaService.findOne(uslugaId);

		if (usluga == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(usluga);
	}

	/* update usluge po id-u */
	@RequestMapping(value = "/api/usluge/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usluga> updateUsluge(
			@PathVariable(value = "id") Long uslugaId,
			@Valid @RequestBody Usluga uslugaDetalji) {

		Usluga usluga = uslugaService.findOne(uslugaId);
		if (usluga == null) {
			return ResponseEntity.notFound().build();
		}

		usluga.setHotel(uslugaDetalji.getHotel());
		usluga.setOpis(uslugaDetalji.getOpis());

		Usluga updateHotel = uslugaService.save(usluga);
		return ResponseEntity.ok().body(updateHotel);
	}

	/* brisanje usluge */
	@RequestMapping(value = "/api/usluge/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usluga> deleteUsluga(
			@PathVariable(value = "id") Long uslugaId) {
		Usluga usluga = uslugaService.findOne(uslugaId);

		if (usluga != null) {
			uslugaService.remove(uslugaId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
