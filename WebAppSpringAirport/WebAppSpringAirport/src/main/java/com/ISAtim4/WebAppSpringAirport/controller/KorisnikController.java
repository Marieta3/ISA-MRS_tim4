package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.Collection;

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

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;

@RestController
public class KorisnikController {
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value = "/api/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Korisnik>> getKorisnici() {
		logger.info("> getKorisnici");

		Collection<Korisnik> korisnici = korisnikService.findAll();

		logger.info("< getKorisnici");
		return new ResponseEntity<Collection<Korisnik>>(korisnici, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> getKorisnik(@PathVariable("id") Long id) {
		logger.info("> getKorisnik id:{}", id);
		Korisnik korisnik = korisnikService.findOne(id);
		if (korisnik == null) {
			return new ResponseEntity<Korisnik>(HttpStatus.NOT_FOUND);
		}
		logger.info("< getKorisnik id:{}", id);
		return new ResponseEntity<Korisnik>(korisnik, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> createKorisnik(@RequestBody Korisnik korisnik) throws Exception {
		logger.info("> createKorisnik");
		Korisnik snimljenKorisnik = korisnikService.create(korisnik);
		logger.info("< createKorisnik");
		return new ResponseEntity<Korisnik>(snimljenKorisnik, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> updateKorisnik(
			@RequestBody Korisnik korisnik) throws Exception {
		logger.info("> updateKorisnik id:{}", korisnik.getId());
		Korisnik updatedKorisnik = korisnikService.update(korisnik);
		if (updatedKorisnik == null) {
			System.out.println("Usao sam ovde");
			return new ResponseEntity<Korisnik>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("< updateKorisnik id:{}", korisnik.getId());
		return new ResponseEntity<Korisnik>(updatedKorisnik, HttpStatus.OK);
	}
}
